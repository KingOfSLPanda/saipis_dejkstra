package com.portfl.configuration;

import com.portfl.constants.Constants;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(Constants.Packages.DAO)
@EnableJpaRepositories(basePackages = Constants.Packages.DAO, entityManagerFactoryRef = "emf")
public class RepositoryConfiguration {
}
