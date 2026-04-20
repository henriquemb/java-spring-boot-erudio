package com.github.henriquemb.services;

import com.github.henriquemb.controllers.BookController;
import com.github.henriquemb.data.dto.BookDTO;
import com.github.henriquemb.exception.RequiredObjectIsNullException;
import com.github.henriquemb.exception.ResourceNotFoundException;
import com.github.henriquemb.mapper.ObjectMapper;
import com.github.henriquemb.model.Book;
import com.github.henriquemb.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
	// Criar a instância do logger
	private final Logger logger = LoggerFactory.getLogger(BookService.class.getName());

	// Cria a instância do repositório
	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	// Busca todos os livros
	public List<BookDTO> findAll() {
		// Registra mensagem de log
		logger.info("Finding all books.");

		// Busca todos os livros do repositório
		List<Book> books = bookRepository.findAll();

		// Adiciona os links hateoas ao livros
		return ObjectMapper.parseListObjects(books, BookDTO.class)
				.stream()
				.peek(bookDTO -> addHateoasLinks(bookDTO, bookDTO.getId()))
				.toList();
	}

	// Busca um livro pelo identificador
	public BookDTO findById(long id) {
		// Registra o log
		logger.info("Finding book with id {}.", id);

		// Busca o livro no repositório e caso não encontre lança uma exceção
		Book book = bookRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Book with id " + id + " not found.")
		);

		// Realiza o map de Book para BookDTO
		BookDTO bookDTO = ObjectMapper.parseObject(book, BookDTO.class);

		// Adiciona os links hateoas
		addHateoasLinks(bookDTO, bookDTO.getId());
		return bookDTO;
	}

	public BookDTO create(BookDTO bookDTO) {
		// Lança exceção em caso de objeto vazio
		if (bookDTO == null)
			throw new RequiredObjectIsNullException();

		logger.info("Creating book {}.", bookDTO);

		// Realizar o map de BookDTO para Book
		Book book = ObjectMapper.parseObject(bookDTO, Book.class);

		// Salva o livro
		Book savedBook = bookRepository.save(book);

		// Realiza o map do livro salvo (Book) para BookDTO
		BookDTO savedBookDTO = ObjectMapper.parseObject(savedBook, BookDTO.class);

		// Adiciona os links hateoas ao livro
		addHateoasLinks(savedBookDTO, savedBookDTO.getId());
		return savedBookDTO;
	}

	public BookDTO update(long id, BookDTO bookDTO) {
		// Verifica se possui algum parâmetro nulo
		if (id <= 0 || bookDTO == null)
			throw new RequiredObjectIsNullException();

		// Registra o log
		logger.info("Updating book with id {} to {}.", id, bookDTO);

		// Verifica se o identificador do livro enviado existe
		Book book = bookRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Book with id " + id + " not found.")
		);

		// Atualiza os dados enviados
		book.setTitle(bookDTO.getTitle());
		book.setAuthor(bookDTO.getAuthor());
		book.setLaunchDate(bookDTO.getLaunchDate());
		book.setPrice(bookDTO.getPrice());

		// Atualiza o livro
		Book savedBook = bookRepository.save(book);

		// Realiza o map para BookDTO
		BookDTO savedBookDTO = ObjectMapper.parseObject(savedBook, BookDTO.class);

		// Adiciona os links hateoas
		addHateoasLinks(savedBookDTO, savedBookDTO.getId());
		return savedBookDTO;
	}

	public void delete(long id) {
		// Verifica se o identificador é válido
		if (id <= 0)
			throw new RequiredObjectIsNullException();

		// Registra o log
		logger.info("Deleting book with id {}.", id);

		// Verifica o livro existe
		if (!bookRepository.existsById(id))
			throw new ResourceNotFoundException("Book with id " + id + " not found.");

		// Deleta o livro
		bookRepository.deleteById(id);
	}

	private void addHateoasLinks(BookDTO bookDTO, long id) {
		bookDTO.add(
				WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder.methodOn(BookController.class).findById(id)
				).withSelfRel().withType("GET")
		);

		bookDTO.add(
				WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder.methodOn(BookController.class).findAll()
				).withRel("findAll").withType("GET")
		);

		bookDTO.add(
				WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder.methodOn(BookController.class).create(bookDTO)
				).withRel("create").withType("POST")
		);

		bookDTO.add(
				WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder.methodOn(BookController.class).update(id, bookDTO)
				).withRel("update").withType("PUT")
		);

		bookDTO.add(
				WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder.methodOn(BookController.class).delete(id)
				).withRel("delete").withType("DELETE")
		);
	}
}
