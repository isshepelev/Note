package ru.isshepelev.note.infrastructure.service.Impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.isshepelev.note.infrastructure.persistance.entity.Note;
import ru.isshepelev.note.infrastructure.persistance.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@NoArgsConstructor
@Data
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private List<Note> notes;
    private Set<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.notes = user.getNotes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }
}