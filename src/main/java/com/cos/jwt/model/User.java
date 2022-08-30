package com.cos.jwt.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
public class User {

    @Id @GeneratedValue
    private long id;
    private String username;
    private String password;
    private String roles;       // USER, ADMIN 여러개 가능 Role 이라는 enum 타입으로 해도됨


    // ENUM으로 안하고 ,로 구분해서 ROLE을 입력
    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
