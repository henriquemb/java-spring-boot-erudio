package com.github.henriquemb.services;

import com.github.henriquemb.data.dto.PersonDTO;
import com.github.henriquemb.exception.RequiredObjectIsNullException;
import com.github.henriquemb.mapper.ObjectMapper;
import com.github.henriquemb.model.Person;
import com.github.henriquemb.repository.PersonRepository;
import com.github.henriquemb.unitetests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonService personService;

    MockPerson mockPerson;

    @BeforeEach
    void setUp() {
        mockPerson = new MockPerson();
    }

    @Test
    void findById() {
        Person person = mockPerson.mockEntity(1);
        person.setId(1L);
        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        PersonDTO result = personService.findById(1L);

        PersonDTO mockPersonDTO = mockPerson.mockDTO(1);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(mockPersonDTO.getFirstName(), result.getFirstName());
        assertNotNull(mockPersonDTO.getLastName(), result.getLastName());
        assertNotNull(mockPersonDTO.getAddress(), result.getAddress());
        assertNotNull(mockPersonDTO.getGender(), result.getGender());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("/person/1")
                                && link.getType() != null
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("/person")
                                && link.getType() != null
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("/person")
                                && link.getType() != null
                                && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("/person/1")
                                && link.getType() != null
                                && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("/person/1")
                                && link.getType() != null
                                && link.getType().equals("DELETE")
                )
        );
    }

    @Test
    void create() {
        Person person = mockPerson.mockEntity(1);
        Mockito.when(personRepository.save(person)).thenReturn(person);

        PersonDTO personDTO = mockPerson.mockDTO(1);
        PersonDTO result = personService.create(personDTO);

        PersonDTO mockPersonDTO = ObjectMapper.parseObject(mockPerson.mockEntity(1),  PersonDTO.class);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(mockPersonDTO.getFirstName(), result.getFirstName());
        assertNotNull(mockPersonDTO.getLastName(), result.getLastName());
        assertNotNull(mockPersonDTO.getAddress(), result.getAddress());
        assertNotNull(mockPersonDTO.getGender(), result.getGender());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("/person/1")
                                && link.getType() != null
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("/person")
                                && link.getType() != null
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("/person")
                                && link.getType() != null
                                && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("/person/1")
                                && link.getType() != null
                                && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("/person/1")
                                && link.getType() != null
                                && link.getType().equals("DELETE")
                )
        );
    }

    @Test
    void createWithNull() {
        Exception e = assertThrows(RequiredObjectIsNullException.class, () -> personService.create(null));

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Person person = mockPerson.mockEntity(1);
        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        Mockito.when(personRepository.save(person)).thenReturn(person);

        PersonDTO personDTO = mockPerson.mockDTO(1);
        PersonDTO result = personService.update(1L, personDTO);

        PersonDTO mockPersonDTO = ObjectMapper.parseObject(mockPerson.mockEntity(1),  PersonDTO.class);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(mockPersonDTO.getFirstName(), result.getFirstName());
        assertNotNull(mockPersonDTO.getLastName(), result.getLastName());
        assertNotNull(mockPersonDTO.getAddress(), result.getAddress());
        assertNotNull(mockPersonDTO.getGender(), result.getGender());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("/person/1")
                                && link.getType() != null
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("/person")
                                && link.getType() != null
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("/person")
                                && link.getType() != null
                                && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("/person/1")
                                && link.getType() != null
                                && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("/person/1")
                                && link.getType() != null
                                && link.getType().equals("DELETE")
                )
        );
    }

    @Test
    void updateWithNull() {
        Exception e = assertThrows(RequiredObjectIsNullException.class, () -> personService.update(0, null));

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Mockito.when(personRepository.existsById(1L)).thenReturn(true);

        personService.delete(1L);

        verify(personRepository, times(1)).existsById(1L);
        verify(personRepository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void findAll() {
    }
}