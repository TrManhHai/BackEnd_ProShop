package com.vti.entity.film;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Film_Photo")
public class FilmPhoto {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path_name")
    private String pathName;

    @Column(name = "type_photo")
    private String typePhoto;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film_id;
}
