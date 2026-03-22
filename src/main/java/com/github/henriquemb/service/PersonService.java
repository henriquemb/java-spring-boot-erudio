package com.github.henriquemb.service;

import com.github.henriquemb.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            people.add(mockPerson(i));
        }

        return people;
    }

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

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("FirstName " + i);
        person.setLastName("LastName " + i);
        person.setAddress("Address " + i);
        person.setGender("Gender " + i);

        return person;
    }
}
