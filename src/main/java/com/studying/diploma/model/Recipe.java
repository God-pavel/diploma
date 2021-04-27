package com.studying.diploma.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Ingredient> ingredients;

    @ManyToOne(fetch = FetchType.EAGER)
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "users_rated",
            joinColumns = @JoinColumn(name = "recipies_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Set<User> usersRated;

    @Column(name = "rate", nullable = false)
    private Integer rate;

    @Column(name = "time", nullable = false)
    private Integer time;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private RecipeCategory recipeCategory;

    @Column(name = "total_energy", nullable = false)
    private BigDecimal totalEnergy;


}
