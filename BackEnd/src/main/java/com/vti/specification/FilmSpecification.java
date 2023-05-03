package com.vti.specification;

import com.vti.entity.film.Film;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class FilmSpecification {
    @SuppressWarnings("deprecation")
    public static Specification<Film> buildWhere(String search) {

        if (StringUtils.isEmpty(search)) {
            return null;
        }

        search = search.trim();

        CustomSpecification name = new CustomSpecification("title", search);

        return Specification.where(name);
    }


}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<Film>{
    @NonNull
    private String field;
    @NonNull
    private Object value;

    @Override
    public Predicate toPredicate(
            Root<Film> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {

        if (field.equalsIgnoreCase("title")) {
            return criteriaBuilder.like(root.get("title"), "%" + value.toString() + "%");
        }

        return null;
    }
}