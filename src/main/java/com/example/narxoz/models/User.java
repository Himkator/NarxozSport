package com.example.narxoz.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity(name="users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String skFk;
    @Column(length = 255000)
    private String password;
    private String FIO;
    private String mail;
    private String role;

    @OneToOne
    @JoinColumn
    private Student student;

    @OneToOne
    @JoinColumn
    private Coach coach;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String userRole = this.getRole(); // Update based on your actual role field name
        if (userRole == null) {
            return List.of();  // Empty list if no role is set
        }
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + userRole));
    }

    @Override
    public String getUsername() {
        return skFk;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

