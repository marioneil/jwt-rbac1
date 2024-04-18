package com.weather.rbac1.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AppUser {
	  private Long id;

	  private String username;

	  private String email;

	  private String password;

	  private Set<UserRole> roles = new HashSet<>();
	  
	  public AppUser(String username, String email, String password) {
		    this.username = username;
		    this.email = email;
		    this.password = password;
		  }
}