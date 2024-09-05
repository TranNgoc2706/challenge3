package com.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.DTO.ResponseData;
import com.example.demo.Service.ProductService;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.DTO.ApiResponse;

@RestController
@RequestMapping(path = "/products", produces = "application/json")
public class ProductController {

    @Autowired
    private ProductService productService;

   @GetMapping("/list")
    public ResponseEntity<ApiResponse<ResponseData<ProductDTO>>> getProducts(@RequestParam int page, @RequestParam int take) {
        Page<ProductDTO> productPage = productService.getProducts(page, take);

        ResponseData<ProductDTO> responseData = new ResponseData<>(
            productPage.getContent(),
            productPage.getNumber(),
            productPage.getSize(),
            (int) productPage.getTotalElements(),
            productPage.getTotalPages(),
            productPage.hasPrevious(),
            productPage.hasNext()
        );

        ApiResponse<ResponseData<ProductDTO>> response = new ApiResponse<>(     
            true, 
            responseData, 
            "Success",
            "/products/list",
            LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseData<ProductDTO>>> getProductById(@PathVariable Long id) {
        try {
            ProductDTO product = productService.getProductById(id);

            ResponseData<ProductDTO> responseData = new ResponseData<>(
                List.of(product), 
                0,                
                1,               
                1,             
                1,               
                false,        
                false           
            );

            ApiResponse<ResponseData<ProductDTO>> response = new ApiResponse<>(true, responseData, "Product found", "/products/" + id, LocalDateTime.now());
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ResponseData<ProductDTO> responseData = new ResponseData<>(
                Collections.emptyList(), 
                0,
                0,
                0,
                0,
                false,
                false
            );

            ApiResponse<ResponseData<ProductDTO>> response = new ApiResponse<>(false, responseData, e.getMessage(), "/products/" + id, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<ApiResponse<ResponseData<ProductDTO>>> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO product = productService.createProduct(productDTO);

            ResponseData<ProductDTO> responseData = new ResponseData<>(
                List.of(product),
                0,
                1,
                1,
                1,
                false,
                false
            );

            ApiResponse<ResponseData<ProductDTO>> response = new ApiResponse<>(true, responseData, "Product created successfully", "/products/new", LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ResponseData<ProductDTO> responseData = new ResponseData<>(
                Collections.emptyList(),
                0,
                0,
                0,
                0,
                false,
                false
            );

            ApiResponse<ResponseData<ProductDTO>> response = new ApiResponse<>(false, responseData, "Failed to create product: " + e.getMessage(), "/products/new", LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseData<ProductDTO>>> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);

            ResponseData<ProductDTO> responseData = new ResponseData<>(
                Collections.emptyList(), // Danh sách rỗng sau khi xóa sản phẩm
                0,
                0,
                0,
                0,
                false,
                false
            );

            ApiResponse<ResponseData<ProductDTO>> response = new ApiResponse<>(true, responseData, "Product deleted successfully", "/products/" + id, LocalDateTime.now());
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ResponseData<ProductDTO> responseData = new ResponseData<>(
                Collections.emptyList(),
                0,
                0,
                0,
                0,
                false,
                false
            );

            ApiResponse<ResponseData<ProductDTO>> response = new ApiResponse<>(false, responseData, e.getMessage(), "/products/" + id, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseData<ProductDTO> responseData = new ResponseData<>(
                Collections.emptyList(),
                0,
                0,
                0,
                0,
                false,
                false
            );

            ApiResponse<ResponseData<ProductDTO>> response = new ApiResponse<>(false, responseData, "An error occurred while deleting the product: " + e.getMessage(), "/products/" + id, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseData<ProductDTO>>> updateProduct(@PathVariable Long id, @RequestBody ProductDTO updatedProductDTO) {
        try {
            ProductDTO updatedProduct = productService.updateProduct(id, updatedProductDTO);

            ResponseData<ProductDTO> responseData = new ResponseData<>(
                List.of(updatedProduct),
                0,
                1,
                1,
                1,
                false,
                false
            );

            ApiResponse<ResponseData<ProductDTO>> response = new ApiResponse<>(true, responseData, "Product updated successfully", "/products/" + id, LocalDateTime.now());
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ResponseData<ProductDTO> responseData = new ResponseData<>(
                Collections.emptyList(),
                0,
                0,
                0,
                0,
                false,
                false
            );

            ApiResponse<ResponseData<ProductDTO>> response = new ApiResponse<>(false, responseData, e.getMessage(), "/products/" + id, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseData<ProductDTO> responseData = new ResponseData<>(
                Collections.emptyList(),
                0,
                0,
                0,
                0,
                false,
                false
            );

            ApiResponse<ResponseData<ProductDTO>> response = new ApiResponse<>(false, responseData, "Failed to update product: " + e.getMessage(), "/products/" + id, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
