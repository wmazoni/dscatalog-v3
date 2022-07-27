package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.factories.RoleFactory;
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
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private long existingId;
    private long nonExistingId;

    private long countTotalRoles;

    @BeforeEach
    void setup() {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalRoles = 2L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {

        roleRepository.deleteById(existingId);

        Optional<Role> result = roleRepository.findById(existingId);

        assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

        assertThrows(EmptyResultDataAccessException.class, () -> {
            roleRepository.deleteById(nonExistingId);
        });
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

        Role role = RoleFactory.createRole();
        role.setId(null);

        role = roleRepository.save(role);

        assertNotNull(role.getId());
        assertEquals(countTotalRoles + 1L, role.getId());

    }

    @Test
    public void findByIdShoulReturnObjectWhenIdExist() {

        Optional<Role> result = roleRepository.findById(existingId);

        assertTrue(result.isPresent());

    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Optional<Role> result = roleRepository.findById(nonExistingId);

        assertTrue(result.isEmpty());
    }
}
