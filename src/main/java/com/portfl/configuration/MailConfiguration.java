package com.portfl.configuration;

import com.portfl.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:email.properties")
public class MailConfiguration {

    @Autowired
    Environment env;

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() {
        final JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        try {
            mailSenderImpl.setHost(env.getRequiredProperty("smtp.host"));
            mailSenderImpl.setPort(env.getRequiredProperty("smtp.port", Integer.class));
            mailSenderImpl.setProtocol(env.getRequiredProperty("smtp.protocol"));
            mailSenderImpl.setUsername(env.getRequiredProperty("smtp.username"));
            mailSenderImpl.setPassword(env.getRequiredProperty("smtp.password"));
        } catch (IllegalStateException ise) {
            throw ise;
        }
        final Properties javaMailProps = new Properties();
        javaMailProps.put("mail.smtp.auth", true);
        javaMailProps.put("mail.smtp.starttls.enable", true);
        mailSenderImpl.setJavaMailProperties(javaMailProps);
        return mailSenderImpl;
    }

}
