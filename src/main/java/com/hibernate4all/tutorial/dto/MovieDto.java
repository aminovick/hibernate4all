package com.hibernate4all.tutorial.dto;

import com.hibernate4all.tutorial.domaine.Certification;

/**
 * Created by Lenovo on 14/04/2022.
 */
public class MovieDto {
    private Long id;
    private String name;
    private String description;
    private Certification certification;
    private String image;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Certification getCertification() {
        return certification;
    }

    public String getImage() {
        return image;
    }

    public MovieDto setId(Long id) {
        this.id = id;
        return this;
    }

    public MovieDto setName(String name) {
        this.name = name;
        return this;
    }

    public MovieDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public MovieDto setCertification(Certification certification) {
        this.certification = certification;
        return this;
    }

    public MovieDto setImage(String image) {
        this.image = image;
        return this;
    }
}

