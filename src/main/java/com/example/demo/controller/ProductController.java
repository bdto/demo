package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.ProductDTO;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // GET TODOS
    // http://localhost:8080/api/products
    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(sort[1]), sort[0]));

        Page<ProductDTO> products = productService.getAllProducts(pageable);

        return ResponseEntity.ok(
                ApiResponse.success("Productos obtenidos", products)
        );
    }


    // GET POR ID
    // http://localhost:8080/api/products/1
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {

        ProductDTO product = productService.getProductById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Producto encontrado", product)
        );
    }


    // GET POR CATEGORIA
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<ProductDTO> products =
                productService.getProductsByCategory(categoryId, pageable);

        return ResponseEntity.ok(
                ApiResponse.success("Productos por categoria", products)
        );
    }


    // BUSCAR
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<ProductDTO> products =
                productService.searchProducts(keyword, pageable);

        return ResponseEntity.ok(
                ApiResponse.success("Resultados de busqueda", products)
        );
    }



    // POST CREAR PRODUCTO
    // http://localhost:8080/api/products
    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(
            @RequestBody ProductDTO dto) {

        ProductDTO product = productService.createProduct(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Producto creado", product));
    }



    // PUT ACTUALIZAR PRODUCTO
    // http://localhost:8080/api/products/1
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDTO dto) {

        ProductDTO product = productService.updateProduct(id, dto);

        return ResponseEntity.ok(
                ApiResponse.success("Producto actualizado", product)
        );
    }



    // DELETE PRODUCTO
    // http://localhost:8080/api/products/1
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                ApiResponse.success("Producto eliminado", null)
        );
    }

}