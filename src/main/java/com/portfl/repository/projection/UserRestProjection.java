package com.portfl.repository.projection;

import com.portfl.model.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "user", types = User.class)
public interface UserRestProjection {
    String getUsername();
    String getEmail();
}
