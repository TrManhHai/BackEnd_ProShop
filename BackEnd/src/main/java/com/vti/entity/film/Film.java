package com.vti.entity.film;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Film", uniqueConstraints = {@UniqueConstraint(columnNames = "title"),
        @UniqueConstraint(columnNames = "vn_title")})
public class Film implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",nullable = false)
    @Size(max = 150)
    private String title;

    @Column(name = "vn_title",nullable = false)
    @Size(max = 150)
    private String vnTitle;

    @Lob
    @Column(name = "story_line",nullable = false)
    private String content;

    @Column(name = "released_at", length = 100)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date releasedAt;

    @Column(name = "duration",nullable = false)
    private Short duration;

    @Lob
    @Column(name = "poster_path")
    private String poster_path;

    @Enumerated(EnumType.STRING)
    @Column(name = "`type`")
    private ETypeFilm typeFilm;

    @Column(name = "gross")
    private Long gross;

    @Column(name = "seasons")
    private Short seasons;

    @Column(name = "episodes")
    private Integer episodes;

    @OneToMany(mappedBy = "film_id")
    private List<FilmPhoto> photos;

}
