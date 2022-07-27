package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.factories.UserFactory;
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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private long existingId;
    private long nonExistingId;

    private long countTotalUsers;

    @BeforeEach
    void setup() {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalUsers = 2L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {

        userRepository.deleteById(existingId);

        Optional<User> result = userRepository.findById(existingId);

        assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

        assertThrows(EmptyResultDataAccessException.class, () -> {
            userRepository.deleteById(nonExistingId);
        });
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

        User user = UserFactory.createUser();
        user.setId(null);

        user = userRepository.save(user);

        assertNotNull(user.getId());
        assertEquals(countTotalUsers + 1L, user.getId());

    }

    @Test
    public void findByIdShoulReturnObjectWhenIdExist() {

        Optional<User> result = userRepository.findById(existingId);

        assertTrue(result.isPresent());

    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Optional<User> result = userRepository.findById(nonExistingId);

        assertTrue(result.isEmpty());
    }
}
