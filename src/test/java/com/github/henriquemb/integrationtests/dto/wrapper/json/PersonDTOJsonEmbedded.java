package com.github.henriquemb.integrationtests.dto.wrapper.json;

import com.github.henriquemb.integrationtests.dto.PersonDTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PersonDTOJsonEmbedded implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

	List<PersonDTO> people;

	public PersonDTOJsonEmbedded() {
	}

	public List<PersonDTO> getPeople() {
		return people;
	}

	public void setPeople(List<PersonDTO> people) {
		this.people = people;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PersonDTOJsonEmbedded that)) return false;
		return Objects.equals(getPeople(), that.getPeople());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getPeople());
	}
}
