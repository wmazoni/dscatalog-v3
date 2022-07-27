package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.factories.CategoryFactory;
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
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;

    private long countTotalCategories;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalCategories = 3L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {

        categoryRepository.deleteById(existingId);

        Optional<Category> result = categoryRepository.findById(existingId);

        assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

        assertThrows(EmptyResultDataAccessException.class, () -> {
            categoryRepository.deleteById(nonExistingId);
        });
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

        Category category = CategoryFactory.createCategory();
        category.setId(null);

        category = categoryRepository.save(category);

        assertNotNull(category.getId());
        assertEquals(countTotalCategories + 1L, category.getId());

    }

    @Test
    public void findByIdShoulReturnObjectWhenIdExist() {

        Optional<Category> result = categoryRepository.findById(existingId);

        assertTrue(result.isPresent());

    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Optional<Category> result = categoryRepository.findById(nonExistingId);

        assertTrue(result.isEmpty());
    }
}
