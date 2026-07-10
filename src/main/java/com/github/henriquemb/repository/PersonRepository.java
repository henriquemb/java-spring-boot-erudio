package com.github.henriquemb.repository;

import com.github.henriquemb.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {
	@Modifying
	@Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
	void disable(@Param("id") Long id);
}
