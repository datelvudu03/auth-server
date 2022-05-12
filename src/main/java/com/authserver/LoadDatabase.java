package com.authserver;

import com.authserver.entity.AppUser;
import com.authserver.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;


@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(AppUserRepository repository) {

        return args -> {
            Random random = new Random();
            log.info("Preloading " + repository.save(new AppUser(random.nextLong(), "admin","admin","admin")));
            for (int i = 0; i < 100; i++) {
                log.info("Preloading " + repository.save(new AppUser(random.nextLong(), "user "+i,"user","user")));
            }


        };
    }
}
