package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Buscar productos activos por categoria
    List<Product> findByCategoryIdAndActiveTrue(Long categoryId);

    // Buscar productos activos por categoria (paginado)
    Page<Product> findByCategoryIdAndActiveTrue(Long categoryId, Pageable pageable);

    // Busqueda por nombre o descripcion (LIKE, case-insensitive)
    @Query("SELECT p FROM Product p WHERE p.active = true AND " +
           "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Product> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // Todos los productos activos (paginado)
    Page<Product> findByActiveTrue(Pageable pageable);

    // Contar productos por categoria
    long countByCategoryId(Long categoryId);
}