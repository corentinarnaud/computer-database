package com.excilys.arnaud.springConfig;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.excilys.arnaud.persistence.PersistenceConfig;
import com.excilys.arnaud.service.ServiceConfig;


@WebAppConfiguration
@EnableWebMvc
@ComponentScan
@Import(value = { 
        ServiceConfig.class,
        ViewConfig.class,
        PersistenceConfig.class
} )

public class MainConfig {

    private static final Logger LOG = LoggerFactory.getLogger(MainConfig.class);

    @Autowired
    private Environment         env;

 
    @PostConstruct
    public void initApp() {
        LOG.info("Looking for Spring profiles...");
        if (env.getActiveProfiles().length == 0) {
            LOG.info("No Spring profile configured, running with default configuration.");
        } else {
            for (String profile : env.getActiveProfiles()) {
                LOG.info("Detected Spring profile: {}", profile);
            }
        }
    }
}