package com.vti.payload.form;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateFilmForm {
    @NotBlank(message = "title not blank")
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
}
