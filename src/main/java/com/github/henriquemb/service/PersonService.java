package com.github.henriquemb.service;

import com.github.henriquemb.model.Person;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(long id) {
        logger.info("Finding person with id " + id);

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Moacir Henrique");
        person.setLastName("Morais Baruffi");
        person.setAddress("Uberlândia - MG");
        person.setGender("Male");

        return person;
    }
}
