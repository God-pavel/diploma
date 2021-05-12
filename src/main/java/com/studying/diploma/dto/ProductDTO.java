package com.studying.diploma.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {
    private Long id;
    private String name;
    private BigDecimal energy;
}