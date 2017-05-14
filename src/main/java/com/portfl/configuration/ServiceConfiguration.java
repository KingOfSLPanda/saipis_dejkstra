package com.portfl.configuration;

import com.portfl.constants.Constants;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(Constants.Packages.SERVICES)
public class ServiceConfiguration {
}
