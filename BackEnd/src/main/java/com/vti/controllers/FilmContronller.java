package com.vti.controllers;


import com.vti.payload.dto.FilmDTO;
import com.vti.entity.film.Film;
import com.vti.payload.form.CreateFilmForm;
import com.vti.payload.form.UpdateFilmForm;
import com.vti.services.IFilmSevice;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/films")
@CrossOrigin("*")
public class FilmContronller {

    @Autowired
    private IFilmSevice service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public Page<FilmDTO> getAllFilm(Pageable pageable, @RequestParam(value = "search", required = false) String search) {
        Page<Film> entityPages = service.getAllFilm(pageable, search);

        // convert entities --> dtos
        List<FilmDTO> dtos = modelMapper.map(
                entityPages.getContent(),
                new TypeToken<List<FilmDTO>>() {
                }.getType());

        Page<FilmDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

        return dtoPages;
    }

    @PostMapping()
    public ResponseEntity<?> createFilm(@RequestBody @Valid CreateFilmForm form) {
        service.createFilm(form);
        return new ResponseEntity<String>("Create successfully!", HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateFilm(@PathVariable(name = "id") Long id,@RequestBody @Valid UpdateFilmForm form) {
        service.updateFilm(id,form);
        return new ResponseEntity<String>("Update successfully!", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteFilm(@PathVariable(name = "id") Long id) {
        service.deleteFilm(id);
        return new ResponseEntity<String>("Delete successfully!", HttpStatus.OK);
    }
}
