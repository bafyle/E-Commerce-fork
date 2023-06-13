package com.vodafone.ecommerce.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class SecurityUser implements UserDetails
{
    private User user;
    public SecurityUser(User user)
    {
        this.user = user;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return new ArrayList<SimpleGrantedAuthority>()
        {{
            add(new SimpleGrantedAuthority(user.getRole()));
        }};
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername()
    {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
