package com.anastas.carsshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse implements Serializable {
	private static final long serialVersionUID = 5022733775368733609L;

	private String username;
	private List<String> authorities;
	private String jwtToken;
}
