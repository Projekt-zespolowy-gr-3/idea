package pl.dmcs.idea.security;

import lombok.Data;

@Data
public class JwtRequest {

    private String login;
    private String password;
}
