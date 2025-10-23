package com.Online.Quiz.Application.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    private String token;
    private String type;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
