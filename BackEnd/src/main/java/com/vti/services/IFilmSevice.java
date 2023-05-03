package com.vti.services;

import com.vti.entity.film.Film;
import com.vti.payload.form.CreateFilmForm;
import com.vti.payload.form.UpdateFilmForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFilmSevice {

    Page<Film> getAllFilm(Pageable pageable,String search);

    void createFilm(CreateFilmForm form);

    void updateFilm(Long id, UpdateFilmForm form);

    void deleteFilm(Long id);
}
