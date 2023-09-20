package com.kibong.shoppingwiki;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ShoppingWikiApplication {

    public static void main(String[] args) {

        SpringApplication.run(ShoppingWikiApplication.class, args);
    }

}
