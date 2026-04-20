package com.github.henriquemb.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({ "id", "title", "author", "launch_date", "price" })
public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private String author;

	@JsonProperty("launch_data")
	@JsonFormat(pattern = "DD/MM/YYYY")
	private Date launchDate;

	@JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
	private BigDecimal price;

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BookDTO bookDTO)) return false;
		if (!super.equals(o)) return false;
		return Objects.equals(getId(), bookDTO.getId()) && Objects.equals(getTitle(), bookDTO.getTitle()) && Objects.equals(getAuthor(), bookDTO.getAuthor()) && Objects.equals(getLaunchDate(), bookDTO.getLaunchDate()) && Objects.equals(getPrice(), bookDTO.getPrice());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getId(), getTitle(), getAuthor(), getLaunchDate(), getPrice());
	}

	@Override
	public String toString() {
		return "BookDTO{" +
				"id=" + id +
				", title='" + title + '\'' +
				", author='" + author + '\'' +
				", launchDate=" + launchDate +
				", price=" + price +
				'}';
	}
}
