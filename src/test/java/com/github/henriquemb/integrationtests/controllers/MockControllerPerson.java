package com.github.henriquemb.integrationtests.controllers;

import com.github.henriquemb.integrationtests.dto.PersonDTO;

public class MockControllerPerson {
	private final PersonDTO person;

	public MockControllerPerson() {
		person = new PersonDTO();
		mockPerson();
	}

	public PersonDTO getPerson() {
		return person;
	}

	public void updateMockPerson() {
		person.setFirstName("Moacir");
		person.setLastName("Baruffi");
	}

	private void mockPerson() {
		person.setFirstName("Richard");
		person.setLastName("Stallman");
		person.setAddress("New York City - New York - USA");
		person.setGender("Male");
		person.setEnabled(true);
	}
}
