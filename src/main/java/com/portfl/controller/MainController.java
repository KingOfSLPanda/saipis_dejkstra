package com.portfl.controller;

import com.portfl.model.*;
import com.portfl.service.Solution;
import com.portfl.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String homePage(Model model) {
        Solution solution = new Solution();
        model.addAttribute("solution", solution);
        return "home";
    }

    @GetMapping(value = "/profile/{profileId}")
    public String profilePage(@PathVariable Long profileId, Model model) {
        if (profileId != -1) {
            User user = userService.findOne(profileId);
            if (user == null) {
                return "redirect:/";
            } else {
                model.addAttribute("user", user);
                return "profile";
            }
        } else {
            return "redirect:/auth/login";
        }
    }

    @GetMapping(value = "/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping(value = "/searchByParam")
    public String searchByParam(Model model) {
        SearchForm searchForm = new SearchForm();
        model.addAttribute("searchForm", searchForm);
        return "searchByParam";
    }

    @PostMapping(value = "/enter")
    public String enter(@Valid Solution solution, Model model) throws IOException {
        if(solution.getN()<solution.getResult() || solution.getN()<solution.getStart())
            return "home";
        solution.setM(solution.getN() * (solution.getN() - 1));
        solution.setAdj(new ArrayList[solution.getN()]);
        ArrayList<Integer> arrayLists[] = new ArrayList[solution.getN()];
        ArrayList<Integer> array = new ArrayList();
        for (int i = 0; i < solution.getN(); i++) {
            array.add(i + 1);
            arrayLists[i] = new ArrayList<Integer>();
        }
        solution.setAdj(arrayLists);
        model.addAttribute("solution", solution);
        model.addAttribute("array", array);
        return "enter";
    }

    @PostMapping(value = "/solution")
    public String solution(Solution solution, Model model, WebRequest request) throws IOException {
        int size = request.getParameterValues("test[]").length;
        int array[] = new int[size];
        for (int i = 0; i < size; i++) {
            String temp = request.getParameterValues("test[]")[i].toString();
            array[i] = Integer.parseInt(temp);
        }
        solution.run(array);
        model.addAttribute("solution", solution);
        return "solution";
    }

    @PostMapping(value = "/searchByParam")
    public String searchByParamSubmit(@Valid SearchForm searchForm, BindingResult result, WebRequest request, Model model) {
        model.addAttribute("users", userService.getUsersByParam(searchForm));
        return "searchUsers";
    }

    @ModelAttribute("genders")
    public Map<Gender, String> initializeRoles() {
        return Arrays.stream(Gender.values()).collect(Collectors.toMap(value -> value, Gender::getLabel));
    }
}
