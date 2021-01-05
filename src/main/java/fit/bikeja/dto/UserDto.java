package fit.bikeja.dto;

import fit.bikeja.entity.User;

public class UserDto {

    private int id;
    private String loginName;
    private String password;
    private String firstName;
    private String surName;

    public UserDto() {
    }

    public UserDto(User entity) {
        this.id = entity.getId();
        this.loginName = entity.getLoginName();
        this.password = entity.getPassword();
        this.firstName = entity.getFirstName();
        this.surName = entity.getSurName();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return this.surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public User toEntity() {
        return new User(this.loginName, this.password, this.firstName, this.surName);
    }
}
