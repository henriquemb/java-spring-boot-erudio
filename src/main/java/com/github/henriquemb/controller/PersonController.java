package com.github.henriquemb.controller;

import com.github.henriquemb.model.Person;
import com.github.henriquemb.services.PersonService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Person> findAll() {
        return service.findAll();
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person create(@RequestBody Person person) {
        return service.create(person);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person update(@PathVariable long id, @RequestBody Person person) {
        return service.update(id, person);
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
