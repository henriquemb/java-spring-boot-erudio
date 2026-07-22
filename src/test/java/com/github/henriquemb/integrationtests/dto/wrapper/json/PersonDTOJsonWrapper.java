package com.github.henriquemb.integrationtests.dto.wrapper.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class PersonDTOJsonWrapper implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

	@JsonProperty("_embedded")
	private PersonDTOJsonEmbedded embeddedDTO;

	public PersonDTOJsonWrapper() {
	}

	public PersonDTOJsonEmbedded getEmbeddedDTO() {
		return embeddedDTO;
	}

	public void setEmbeddedDTO(PersonDTOJsonEmbedded embeddedDTO) {
		this.embeddedDTO = embeddedDTO;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PersonDTOJsonWrapper that)) return false;
		return Objects.equals(getEmbeddedDTO(), that.getEmbeddedDTO());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getEmbeddedDTO());
	}
}
