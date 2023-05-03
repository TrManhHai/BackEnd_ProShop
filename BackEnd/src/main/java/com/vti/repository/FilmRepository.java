
package com.vti.repository;

import com.vti.entity.film.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {

//    Boolean existsByTitle( Film film);
}

