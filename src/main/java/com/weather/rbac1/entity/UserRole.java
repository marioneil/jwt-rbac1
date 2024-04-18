package com.weather.rbac1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRole {

	private Integer id;

	private ERole name;

	public enum ERole {
		ROLE_USER, ROLE_MODERATOR, ROLE_ADMIN, ROLE_HOD, ROLE_PROF
	}
}