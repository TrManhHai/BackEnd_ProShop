package com.vti.services;


import com.vti.entity.film.ETypeFilm;
import com.vti.entity.film.Film;
import com.vti.exceptions.AppException;
import com.vti.exceptions.ErrorResponseBase;
import com.vti.payload.form.CreateFilmForm;
import com.vti.payload.form.UpdateFilmForm;
import com.vti.repository.FilmRepository;
import com.vti.specification.FilmSpecification;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FilmSevice implements IFilmSevice {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<Film> getAllFilm(Pageable pageable, String search) {
        Specification<Film> where = FilmSpecification.buildWhere(search);
        return filmRepository.findAll(where, pageable);
    }

    @Override
    public void createFilm(CreateFilmForm form) {
// omit id field
        TypeMap<CreateFilmForm, Film> typeMap = modelMapper.getTypeMap(CreateFilmForm.class, Film.class);
        if (typeMap == null) { // if not already added
            // skip field
            modelMapper.addMappings(new PropertyMap<CreateFilmForm, Film>() {
                @Override
                protected void configure() {
                    skip(destination.getId());
                }
            });
        }

//        Film film2 = new Film();
//        BeanUtils.copyProperties(form, film2);

        // convert form to entity
        Film film = modelMapper.map(form, Film.class);
        // check duration
//        if (film.getDuration() < 1){
//            throw new AppException(ErrorResponseBase.FILM_DURATION_INVALID);
//        }

        // check tôn tại cảu title

        // check tồn tại cảu vn title
        try {
            filmRepository.save(film);
        } catch (Exception ex) {
            throw new AppException(ex);
        }

    }

    @Override
    public void updateFilm(Long id, UpdateFilmForm form) {
        Optional<Film> optional = filmRepository.findById(id);
        if (!optional.isPresent()) {
            throw new AppException(ErrorResponseBase.NOT_FOUND);
        }
        Film film = optional.get();

        film.setTitle(form.getTitle());
        film.setVnTitle(form.getVnTitle());
        film.setDuration(form.getDuration());
        film.setTypeFilm(ETypeFilm.valueOf(form.getTypeFilm()));
        film.setContent(form.getContent());

        // check tôn tại cảu title
//        if (filmRepository.existsByTitle(film)) {
//            throw new AppException(ErrorResponseBase.FILM_TITLE_INVALID);
//        }
        // check tồn tại cảu vn title

        try {
            filmRepository.save(film);
        } catch (Exception ex) {
            throw new AppException(ex);
        }

    }

    @Override
    public void deleteFilm(Long id) {
        Optional<Film> optional = filmRepository.findById(id);
        if (!optional.isPresent()) {
            throw new AppException(ErrorResponseBase.NOT_FOUND);
        }
        filmRepository.deleteById(id);
    }


}
