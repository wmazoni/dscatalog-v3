package com.devsuperior.dscatalog.factories;

import com.devsuperior.dscatalog.dto.UserDto;
import com.devsuperior.dscatalog.entities.User;

public class UserFactory {

    public static User createUser() {
        return new User(1L, "Davi", "Toledo", "davi@gmail.com", "123456");
    }

    public static UserDto createUserDto() {
        User user = createUser();
        return new UserDto(user);
    }
}
