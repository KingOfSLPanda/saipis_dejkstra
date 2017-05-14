package com.portfl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ProtflApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ProtflApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner bootstrap(GenderDao genderDao, RoleDao roleDao) {
        return (args) -> {
            // save a couple of customers
            genderDao.deleteAll();
            genderDao.save(new Gender("Male"));
            genderDao.save(new Gender("Female"));

            roleDao.deleteAll();
            roleDao.save(new Role("ROLE_USER"));
            roleDao.save(new Role("ROLE_ADMIN"));
        };
    }*/
}
