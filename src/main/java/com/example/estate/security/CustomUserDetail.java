package com.example.estate.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.estate.models.User;

public class CustomUserDetail implements UserDetails {
    private User user;
    public CustomUserDetail(User user){
        this.user=user;
    }
    @Override
    public String getUsername(){
return user.getEmail();
    }
    @Override
    public String getPassword(){
        return user.getPassword();
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
	
	public String getFullName() {
		return user.getFullName();
	}
    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

}
