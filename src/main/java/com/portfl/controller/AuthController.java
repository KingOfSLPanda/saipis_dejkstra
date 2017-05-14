package com.portfl.controller;

import com.portfl.event.OnRegistrationCompleteEvent;
import com.portfl.model.*;
import com.portfl.service.RegistrationService;
import com.portfl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static com.portfl.utils.UrlUtils.getAppUrl;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    public RegistrationService registrationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registrationSubmit(@Valid User user, BindingResult result, WebRequest request, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }
        if (userService.isExistUsername(user.getUsername())) {
            model.addAttribute("existUsername", true);
            return "registration";
        }
        if (userService.isExistEmail(user.getEmail())) {
            model.addAttribute("existEmail", true);
            return "registration";
        }
        userService.create(user);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), getAppUrl(request)));
        return "redirect:/auth/login";
    }

    @GetMapping(value = "/registrationConfirm.html")
    public String registrationConfirm(@RequestParam("token") String token) {
        if (userService.enableAccount(token)) {
            return "redirect:/auth/login";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/edit/{profileId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editAdmin(@PathVariable Long profileId, Model model) {
        User user = userService.findOne(profileId);
        model.addAttribute("user", user);
            return "edit";
    }

    @GetMapping(value = "/edit")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String edit(Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping(value = "/edit")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String editSubmit(@Valid User user, BindingResult result, WebRequest request, Model model) {
        if ((user.getId() != userService.getUser().getId()) && (userService.getUser().getRole().equals("ROLE_USER"))) {
            return "redirect:/";
        }
        if (result.hasErrors()) {
            return "edit";
        }
        if(!(user.getUsername().equals(userService.getUser().getUsername()))){
            userService.update(user);
            return "redirect:/auth/logout";
        }
        userService.update(user);
        return "redirect:/profile/" + user.getId();
    }

    @PostMapping(value = "/edit/{profileId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editSubmitAdmin(@Valid User user, BindingResult result, WebRequest request, Model model) {
        if (result.hasErrors()) {
            return "edit";
        }
        if (userService.isExistUsername(user.getUsername())) {
            model.addAttribute("existUsername", true);
            return "edit";
        }
        if (userService.isExistEmail(user.getEmail())) {
            model.addAttribute("existEmail", true);
            return "edit";
        }
        userService.update(user);
        return "redirect:/profile/" + user.getId();
    }

    @GetMapping(value = "/makeAdmin/{profileId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String makeAdmin(@PathVariable Long profileId) {
        userService.makeAdmin(profileId);
        return "redirect:/users";
    }

    @GetMapping(value = "/makeUser/{profileId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String makeUser(@PathVariable Long profileId) {
        userService.makeUser(profileId);
        return "redirect:/users";
    }

    @GetMapping(value = "/delete/{profileId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long profileId) {
        if (profileId == userService.getUser().getId()) {
            return "redirect:/users";
        } else {
            userService.delete(profileId);
            return "redirect:/users";
        }
    }

    @ModelAttribute("genders")
    public Map<Gender, String> initializeRoles() {
        return Arrays.stream(Gender.values()).collect(Collectors.toMap(value -> value, Gender::getLabel));
    }
}
