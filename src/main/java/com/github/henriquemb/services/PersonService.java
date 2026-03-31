package com.github.henriquemb.services;

import com.github.henriquemb.exception.ResourceNotFoundException;
import com.github.henriquemb.model.Person;
import com.github.henriquemb.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        logger.info("Finding all persons.");

        return personRepository.findAll();
    }

    public Person findById(long id) {
        logger.info(String.format("Finding person with id %d.", id));

        return personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Person with id " + id + " not found.")
        );
    }

    public Person create(Person person) {
        logger.info(String.format("Creating person %s.", person.toString()));

        return personRepository.save(person);
    }

    public Person update(long id, Person person) {
        logger.info(String.format("Updating person id %d to %s.", id, person.toString()));

        Person entity = personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Person with id " + person.getId() + " not found.")
        );

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress()   );
        entity.setGender(person.getGender());

        return personRepository.save(entity);
    }

    public void delete(long id) {
        logger.info(String.format("Deleting person %d.", id));

        if (!personRepository.existsById(id)) {
            throw new ResourceNotFoundException("Person with id " + id + " not found.");
        }

        personRepository.deleteById(id);
    }
}
