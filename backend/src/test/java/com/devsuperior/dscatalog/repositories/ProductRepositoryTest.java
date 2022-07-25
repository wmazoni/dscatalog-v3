package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.factories.ProductFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;

    @BeforeEach
    void setup() {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {

        productRepository.deleteById(existingId);

        Optional<Product> result = productRepository.findById(existingId);
        assertFalse(result.isPresent());

    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

        assertThrows(EmptyResultDataAccessException.class, () -> productRepository.deleteById(nonExistingId));

    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

        Product product = ProductFactory.createProduct();
        product.setId(null);

        product = productRepository.save(product);

        assertNotNull(product.getId());
        assertEquals(countTotalProducts + 1L, product.getId());

    }

    @Test
    public void findByIdShoulReturnObjectWhenIdExist() {

        Optional<Product> result = productRepository.findById(existingId);

        assertTrue(result.isPresent());

    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Optional<Product> result = productRepository.findById(nonExistingId);

        assertTrue(result.isEmpty());
    }
}