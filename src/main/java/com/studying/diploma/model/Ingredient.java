package com.studying.diploma.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "ingridient")

public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @Column(name = "weight")
    private Long total;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "is_weighed", nullable = false)
    private boolean isWeighed;
}
