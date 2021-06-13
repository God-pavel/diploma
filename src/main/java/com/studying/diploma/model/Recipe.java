package com.studying.diploma.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static com.studying.diploma.service.AmazonClient.RECIPE_PHOTO_FOLDER;
import static com.studying.diploma.service.AmazonClient.RECIPE_VIDEO_FOLDER;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recipe_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "text", nullable = false, length = 1000)
    private String text;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Ingredient> ingredients;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<Mark> marks;

    @ManyToOne(fetch = FetchType.EAGER)
    private User creator;

    @Column(name = "rate", nullable = false)
    private Double rate;

    @Column(name = "time", nullable = false)
    private Integer time;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private RecipeCategory recipeCategory;

    @Column(name = "total_energy", nullable = false)
    private BigDecimal totalEnergy;

    @Column(name = "photo")
    private String photo;

    @Column(name = "video")
    private String video;

    @Transient
    public String getPhotosImagePath() {
        if (photo == null || id == null) return null;
        return "https://diploma-files.s3.amazonaws.com/" + RECIPE_PHOTO_FOLDER + photo;
    }

    @Transient
    public String getVideoPath() {
        if (video == null || id == null) return null;
        return "https://diploma-files.s3.amazonaws.com/" + RECIPE_VIDEO_FOLDER + video;
    }

    public void calculateRate() {
        rate = marks.stream()
                .mapToInt(Mark::getMark)
                .average().orElse(0d);
    }
}
