package com.devsuperior.dscatalog.dto;

public class UserInsertDto extends UserDto {

    private String password;

    public UserInsertDto() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
