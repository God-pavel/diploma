package com.studying.diploma.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "temporary_recipe")
public class TemporaryRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "name")
    private String name;

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

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "time")
    private Integer time;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private RecipeCategory recipeCategory;

    @Column(name = "total_energy")
    private BigDecimal totalEnergy;

    public void addIngredient(final Ingredient ingredient){
        ingredients.add(ingredient);
    }

}
