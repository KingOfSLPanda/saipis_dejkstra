package com.portfl.configuration.security;

import com.portfl.model.User;
import com.portfl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class CrmUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    public CrmUserDetailsService() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        try {
            final User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with username: " + username);
            }
            Set<SimpleGrantedAuthority> authoritySet = new HashSet<>();
            authoritySet.add(new SimpleGrantedAuthority(user.getRole().name()));
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(), user.isEnabled(), true,
                    true, true, authoritySet);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}