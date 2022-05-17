package com.authserver;

import com.authserver.entity.AppUser;
import com.authserver.entity.Contact;
import com.authserver.repository.AppUserRepository;
import com.authserver.repository.ContactRepository;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.Random;


@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(AppUserRepository repository, ContactRepository contactRepository) {

        return args -> {
            Faker f = new Faker(new Locale("vi"));
            AppUser appUser = new AppUser("admin","admin","admin");
            Contact contact = new Contact(f.internet().emailAddress(),f.address().streetAddress(),f.address().city(),f.address().zipCode(),appUser);
            log.info("Preloading " + contactRepository.save(contact));
            log.info("Preloading " + repository.save(appUser));

           for (int i = 0; i < 100; i++) {
                appUser = new AppUser( "user "+i,"user","user");
                contact = new Contact(f.internet().emailAddress(),f.address().streetAddress(),f.address().city(),f.address().zipCode(),appUser);
                log.info("Preloading " + contactRepository.save(contact));
                log.info("Preloading " + repository.save(appUser));
            }


        };
    }
}
