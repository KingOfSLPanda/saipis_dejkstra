package com.portfl.service;

import com.portfl.model.*;
import com.portfl.repository.TokenRepository;
import com.portfl.repository.UserRepository;
import com.portfl.utils.DateUtils;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findOne(Long userId) {
        return this.userRepository.findOne(userId);
    }

    public Iterable<User> findAll() {
        return this.userRepository.findAll();
    }

    public List<User> findAllInList() {
        return this.userRepository.findAll();
    }

    @Transactional
    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.ROLE_USER);
        this.userRepository.save(user);
    }

    @Transactional
    public void update(User user) {
        User entity = this.userRepository.findOne(user.getId());

        if (Objects.nonNull(entity)) {
            entity.setUsername(user.getUsername());
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setGender(user.getGender());
            entity.setEmail(user.getEmail());
            this.userRepository.save(entity);
        }
    }

    @Transactional
    public void delete(Long id) {
        this.tokenRepository.deleteByUserId(id);
        this.userRepository.delete(id);
    }

    public boolean isAccountEnabled(String username) {
        return findByUsername(username).isEnabled();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void createVerificationToken(String token, User user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        tokenRepository.save(verificationToken);
    }

    @Transactional
    public boolean enableAccount(String token) {
        try {
            VerificationToken verificationToken = tokenRepository.findByToken(token);
            User user = verificationToken.getUser();
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isExistUsername(String username) {
        if (userRepository.findByUsername(username) != null)
            return true;
        return false;
    }

    public boolean isExistEmail(String email) {
        if (userRepository.findByEmail(email) != null)
            return true;
        return false;
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(authentication)) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                return findByUsername(((UserDetails) principal).getUsername());
            }
            return null;
        }
        return null;
    }

    public void makeAdmin(Long profileId) {
        User user = userRepository.findOne(profileId);
        user.setRole(UserRole.ROLE_ADMIN);
        userRepository.save(user);
    }

    public void makeUser(Long profileId) {
        User user = userRepository.findOne(profileId);
        user.setRole(UserRole.ROLE_USER);
        userRepository.save(user);
    }

    private void findByParamFirstName(String firstName, List<User> userList) {
        Iterator<User> userIterator = userList.iterator();
        while (userIterator.hasNext()) {
            if (!userIterator.next().getFirstName().equals(firstName) && firstName != "") {
                userIterator.remove();
            }
        }
    }

    private void findByParamLastName(String lastName, List<User> userList) {
        Iterator<User> userIterator = userList.iterator();
        while (userIterator.hasNext()) {
            if (!userIterator.next().getLastName().equals(lastName) && lastName != "") {
                userIterator.remove();
            }
        }
    }

    private void findByParamGender(Gender gender, List<User> userList) {
        Iterator<User> userIterator = userList.iterator();
        while (userIterator.hasNext()) {
            if (!userIterator.next().getGender().equals(gender) && gender != null) {
                userIterator.remove();
            }
        }
    }

    public List<User> getUsersByParam(SearchForm searchForm) {
        List<User> userList = findAllInList();
        findByParamFirstName(searchForm.getFirstName(), userList);
        findByParamLastName(searchForm.getLastName(), userList);
        findByParamGender(searchForm.getGender(), userList);
        return userList;
    }
}
