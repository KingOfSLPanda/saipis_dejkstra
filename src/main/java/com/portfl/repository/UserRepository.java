package com.portfl.repository;

import com.portfl.model.User;
import com.portfl.repository.projection.UserRestProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "users", collectionResourceRel = "users", excerptProjection = UserRestProjection.class)
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(@Param("username") String username);
    User findByEmail(@Param("email") String email);

    void delete(User user);
}
