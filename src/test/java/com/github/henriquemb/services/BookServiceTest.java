package com.github.henriquemb.services;

import com.github.henriquemb.data.dto.BookDTO;
import com.github.henriquemb.exception.RequiredObjectIsNullException;
import com.github.henriquemb.mapper.ObjectMapper;
import com.github.henriquemb.model.Book;
import com.github.henriquemb.repository.BookRepository;
import com.github.henriquemb.unitetests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    MockBook mockBook;

    @BeforeEach
    void setUp() {
        mockBook = new MockBook();
    }

    @Test
    void findById() {
        Book book = mockBook.mockEntity(1);
        book.setId(1L);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        BookDTO result = bookService.findById(1L);

        BookDTO mockBookDTO = mockBook.mockDTO(1);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(mockBookDTO.getTitle(), result.getTitle());
        assertEquals(mockBookDTO.getAuthor(), result.getAuthor());
        assertEquals(mockBookDTO.getLaunchDate(), result.getLaunchDate());
        assertEquals(mockBookDTO.getPrice(), result.getPrice());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("/api/v1/book/1")
                                && link.getType() != null
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("/api/v1/book")
                                && link.getType() != null
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("/api/v1/book")
                                && link.getType() != null
                                && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("/api/v1/book/1")
                                && link.getType() != null
                                && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("/api/v1/book/1")
                                && link.getType() != null
                                && link.getType().equals("DELETE")
                )
        );
    }

    @Test
    void create() {
        Book book = mockBook.mockEntity(1);
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        BookDTO bookDTO = mockBook.mockDTO(1);
        BookDTO result = bookService.create(bookDTO);

        BookDTO mockBookDTO = ObjectMapper.parseObject(mockBook.mockEntity(1),  BookDTO.class);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(mockBookDTO.getTitle(), result.getTitle());
        assertEquals(mockBookDTO.getAuthor(), result.getAuthor());
        assertEquals(mockBookDTO.getLaunchDate(), result.getLaunchDate());
        assertEquals(mockBookDTO.getPrice(), result.getPrice());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("/api/v1/book/1")
                                && link.getType() != null
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("/api/v1/book")
                                && link.getType() != null
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("/api/v1/book")
                                && link.getType() != null
                                && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("/api/v1/book/1")
                                && link.getType() != null
                                && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("/api/v1/book/1")
                                && link.getType() != null
                                && link.getType().equals("DELETE")
                )
        );
    }

    @Test
    void createWithNull() {
        Exception e = assertThrows(RequiredObjectIsNullException.class, () -> bookService.create(null));

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Book book = mockBook.mockEntity(1);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        BookDTO bookDTO = mockBook.mockDTO(1);
        BookDTO result = bookService.update(1L, bookDTO);

        BookDTO mockBookDTO = mockBook.mockDTO(1);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(mockBookDTO.getTitle(), result.getTitle());
        assertEquals(mockBookDTO.getAuthor(), result.getAuthor());
        assertEquals(mockBookDTO.getLaunchDate(), result.getLaunchDate());
        assertEquals(mockBookDTO.getPrice(), result.getPrice());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("/api/v1/book/1")
                                && link.getType() != null
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("/api/v1/book")
                                && link.getType() != null
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("/api/v1/book")
                                && link.getType() != null
                                && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("/api/v1/book/1")
                                && link.getType() != null
                                && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("/api/v1/book/1")
                                && link.getType() != null
                                && link.getType().equals("DELETE")
                )
        );
    }

    @Test
    void updateWithNull() {
        Exception e = assertThrows(RequiredObjectIsNullException.class, () -> bookService.update(0, null));

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Mockito.when(bookRepository.existsById(1L)).thenReturn(true);

        bookService.delete(1L);

        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void findAll() {
        List<Book> books = mockBook.mockEntityList();
        when(bookRepository.findAll()).thenReturn(books);
        List<BookDTO> bookDTOS = bookService.findAll();

        assertNotNull(bookDTOS);
        assertEquals(14, bookDTOS.size());

        BookDTO result = bookDTOS.getFirst();

        BookDTO mockBookDTO = mockBook.mockDTO(1);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(mockBookDTO.getTitle(), result.getTitle());
        assertEquals(mockBookDTO.getAuthor(), result.getAuthor());
        assertEquals(mockBookDTO.getLaunchDate(), result.getLaunchDate());
        assertEquals(mockBookDTO.getPrice(), result.getPrice());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("self")
                                && link.getHref().endsWith("/api/v1/book/1")
                                && link.getType() != null
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("/api/v1/book")
                                && link.getType() != null
                                && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("create")
                                && link.getHref().endsWith("/api/v1/book")
                                && link.getType() != null
                                && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("update")
                                && link.getHref().endsWith("/api/v1/book/1")
                                && link.getType() != null
                                && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(
                        link -> link.getRel().value().equals("delete")
                                && link.getHref().endsWith("/api/v1/book/1")
                                && link.getType() != null
                                && link.getType().equals("DELETE")
                )
        );

        verify(bookRepository, times(1)).findAll();
        verifyNoMoreInteractions(bookRepository);
    }
}