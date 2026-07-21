package com.github.henriquemb.services;

import com.github.henriquemb.controllers.PersonController;
import com.github.henriquemb.data.dto.PersonDTO;
import com.github.henriquemb.exception.RequiredObjectIsNullException;
import com.github.henriquemb.exception.ResourceNotFoundException;
import com.github.henriquemb.model.Person;
import com.github.henriquemb.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import static com.github.henriquemb.mapper.ObjectMapper.parseObject;

@Service
public class PersonService {
    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Page<PersonDTO> findAll(Pageable pageable) {
        logger.info("Finding all persons.");

        return personRepository.findAll(pageable)
                .map(person -> {
                    PersonDTO personDTO = parseObject(person, PersonDTO.class);
                    addHateoasLinks(personDTO, person.getId());
                    return personDTO;
                });
    }

    public PersonDTO findById(long id) {
        logger.info("Finding person with id {}.", id);

        Person person = personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Person with id " + id + " not found.")
        );

        PersonDTO personDTO = parseObject(person, PersonDTO.class);
        addHateoasLinks(personDTO, id);
        return personDTO;
    }

    public PersonDTO create(PersonDTO personDTO) {
        if (personDTO == null)
            throw new RequiredObjectIsNullException();

        logger.info("Creating person {}.", personDTO);

        Person person = parseObject(personDTO, Person.class);
        Person savedPerson = personRepository.save(person);
        PersonDTO savedPersonDTO = parseObject(savedPerson, PersonDTO.class);
        addHateoasLinks(savedPersonDTO, savedPersonDTO.getId());
        return savedPersonDTO;
    }

    public PersonDTO update(long id, PersonDTO personDTO) {
        if (id <= 0 || personDTO == null)
            throw new RequiredObjectIsNullException();

        logger.info("Updating person id {} to {}.", id, personDTO);

        Person entity = personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Person with id " + personDTO.getId() + " not found.")
        );

        entity.setFirstName(personDTO.getFirstName());
        entity.setLastName(personDTO.getLastName());
        entity.setAddress(personDTO.getAddress()   );
        entity.setGender(personDTO.getGender());

        Person savedPerson = personRepository.save(entity);
        PersonDTO savedPersonDTO = parseObject(savedPerson, PersonDTO.class);
        addHateoasLinks(savedPersonDTO, id);
        return savedPersonDTO;
    }

    @Transactional
    public PersonDTO disable(long id) {
        logger.info("Disabling person {}.", id);

        if (!personRepository.existsById(id)) {
            throw new ResourceNotFoundException("Person with id " + id + " not found.");
        }

        personRepository.disable(id);
        return findById(id);
    }

    public void delete(long id) {
        logger.info("Deleting person {}.", id);

        if (!personRepository.existsById(id)) {
            throw new ResourceNotFoundException("Person with id " + id + " not found.");
        }

        personRepository.deleteById(id);
    }

    private void addHateoasLinks(PersonDTO personDTO, long id) {
        personDTO.add(
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PersonController.class).findById(id)
            ).withSelfRel().withType("GET")
        );

        personDTO.add(
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PersonController.class).findAll(1, 12, "asc", null)
            ).withRel("findAll").withType("GET")
        );

        personDTO.add(
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PersonController.class).create(personDTO)
            ).withRel("create").withType("POST")
        );

        personDTO.add(
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PersonController.class).update(id, personDTO)
            ).withRel("update").withType("PUT")
        );

        personDTO.add(
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PersonController.class).disable(id)
            ).withRel("disable").withType("PATCH")
        );

        personDTO.add(
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PersonController.class).delete(id)
            ).withRel("delete").withType("DELETE")
        );
    }
}
