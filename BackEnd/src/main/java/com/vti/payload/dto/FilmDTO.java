package com.vti.payload.dto;


import com.vti.entity.film.ETypeFilm;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class FilmDTO {
    @NotBlank(message = "title not Blank")
    private  String title;

    private Date releasedAt;
    private short duration;
    private ETypeFilm TypeFilm;
    private Long gross;
    private Short seasons;
    private Integer episodes;
    private List<FilmPhotodto> photos;

    @Data
    @NoArgsConstructor
    static class FilmPhotodto {
        private int id;
        private String pathName;
        private String typePhoto;
    }
}
