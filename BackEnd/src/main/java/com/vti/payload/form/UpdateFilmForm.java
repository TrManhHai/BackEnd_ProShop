package com.vti.payload.form;


import com.vti.entity.film.FilmPhoto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdateFilmForm {

    private Long id;

    @NotBlank(message = "title không được để trống")
    private String title;

    @NotBlank(message = "vnTitle không được để trống")
    private String vnTitle;

    @NotNull(message = "duration không được để trống")
    private Short duration;

    @NotBlank(message = "poster_path không được để trống")
    private String poster_path;

    @NotBlank(message = "eTypeFilm không được để trống")
    private String typeFilm;

    @NotBlank(message = "content không được để trống")
    private String content;

    private List<FilmPhoto> photos;
}
