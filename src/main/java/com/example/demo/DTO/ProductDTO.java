package com.example.demo.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductDTO {
    private String code;

    private String name;

    private String category;

    private String brand;

    private String type;

    private String description;

    

}
