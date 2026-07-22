package com.github.henriquemb.integrationtests.dto.wrapper.xml;

import com.github.henriquemb.integrationtests.dto.PersonDTO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class PersonDTOXmlWrapper implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

	@XmlElement(name = "content")
	List<PersonDTO> content;

	public PersonDTOXmlWrapper() {
	}

	public List<PersonDTO> getContent() {
		return content;
	}

	public void setContent(List<PersonDTO> content) {
		this.content = content;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PersonDTOXmlWrapper that)) return false;
		return Objects.equals(getContent(), that.getContent());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getContent());
	}
}
