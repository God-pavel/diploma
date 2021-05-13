package com.studying.diploma.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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

    @Column(name = "text", nullable = false)
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

    public void calculateRate(){
        rate = marks.stream()
                .mapToInt(Mark::getMark)
                .average().orElse(0d);
    }
}
