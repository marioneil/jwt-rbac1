package com.weather.rbac1.service;

import com.weather.rbac1.dto.AppUserDetails;
import com.weather.rbac1.entity.AppUser;
import com.weather.rbac1.repo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepo appUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUser = appUserRepo.findByUsername(username);
        if(appUser.isPresent()){
            return AppUserDetails.build(appUser.get());

        };
        return null;
    }
}
