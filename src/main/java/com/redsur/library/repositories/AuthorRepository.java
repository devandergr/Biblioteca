package com.redsur.library.repositories;

import com.redsur.library.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByFirstNameAndLastNameAndNationality(String firstName, String lastName, String nationality);
}
