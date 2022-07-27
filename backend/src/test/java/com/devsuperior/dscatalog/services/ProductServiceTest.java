package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.ProductDto;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.factories.ProductFactory;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;

    @BeforeEach
    void setup() {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        Product product = ProductFactory.createProduct();
        PageImpl<Product> page = new PageImpl<>(List.of(product));

        when(productRepository.findAll((Pageable) any())).thenReturn(page);
        when(productRepository.save(any())).thenReturn(product);
        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        when(categoryRepository.getOne(any())).thenReturn(new Category(2L, "Electronics"));
        doNothing().when(productRepository).deleteById(existingId);
        doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
        doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
    }

    @Test
    public void findAllPagedShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductDto> result = productService.findAllPaged(pageable);

        assertNotNull(result);
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExist() {
        ProductDto obj = productService.findById(existingId);

        assertEquals(1L, obj.getId());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        assertThrows(ResourceNotFoundException.class, () -> productService.findById(nonExistingId));

        verify(productRepository, times(1)).findById(nonExistingId);
    }

    @Test
    public void insertShouldAddNewObject() {
        ProductDto productDto = ProductFactory.createProductDto();

        ProductDto result = productService.insert(productDto);

        assertEquals(1L, result.getId());

    }


    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId() {

        assertThrows(DatabaseException.class, () -> productService.delete(dependentId));

        verify(productRepository, times(1)).deleteById(dependentId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        assertThrows(ResourceNotFoundException.class, () -> productService.delete(nonExistingId));

        verify(productRepository, times(1)).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExist() {

        assertDoesNotThrow(() -> productService.delete(existingId));

        verify(productRepository, times(1)).deleteById(existingId);
    }
}
