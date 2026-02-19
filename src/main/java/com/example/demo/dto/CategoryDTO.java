package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "El nombre de la categoria es obligatorio")
    private String name;

    private String description;

    private String imageUrl;

    private long productCount; // cuantos productos tiene esta categoria
}