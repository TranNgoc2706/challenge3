package com.example.demo.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData<T> {
    private List<T> rows;
    private int page;
    private int take;
    private int itemCount;
    private int pageCount;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
}
