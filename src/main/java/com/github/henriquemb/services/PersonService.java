package com.github.henriquemb.services;

import com.github.henriquemb.data.dto.PersonDTO;
import com.github.henriquemb.exception.ResourceNotFoundException;
import com.github.henriquemb.model.Person;
import com.github.henriquemb.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.henriquemb.mapper.ObjectMapper.parseListObjects;
import static com.github.henriquemb.mapper.ObjectMapper.parseObject;

@Service
public class PersonService {
    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDTO> findAll() {
        logger.info("Finding all persons.");

        List<Person> persons = personRepository.findAll();
        return parseListObjects(persons, PersonDTO.class);
    }

    public PersonDTO findById(long id) {
        logger.info("Finding person with id {}.", id);

        Person person = personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Person with id " + id + " not found.")
        );
        return parseObject(person, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO personDTO) {
        logger.info("Creating person {}.", personDTO.toString());

        Person person = parseObject(personDTO, Person.class);
        Person savedPerson = personRepository.save(person);
        return parseObject(savedPerson, PersonDTO.class);
    }

    public PersonDTO update(long id, PersonDTO personDTO) {
        logger.info("Updating person id {} to {}.", id, personDTO.toString());

        Person entity = personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Person with id " + personDTO.getId() + " not found.")
        );

        entity.setFirstName(personDTO.getFirstName());
        entity.setLastName(personDTO.getLastName());
        entity.setAddress(personDTO.getAddress()   );
        entity.setGender(personDTO.getGender());

        Person savedPerson = personRepository.save(entity);
        return parseObject(savedPerson, PersonDTO.class);
    }

    public void delete(long id) {
        logger.info("Deleting person {}.", id);

        if (!personRepository.existsById(id)) {
            throw new ResourceNotFoundException("Person with id " + id + " not found.");
        }

        personRepository.deleteById(id);
    }
}
