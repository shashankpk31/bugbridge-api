package com.shashankpk.bug_bridge.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Setter
@Getter
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private Set<String> roles;
}
