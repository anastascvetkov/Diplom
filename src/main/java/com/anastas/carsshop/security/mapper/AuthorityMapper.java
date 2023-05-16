package com.anastas.carsshop.security.mapper;

import com.anastas.carsshop.dto.AuthorityDTO;
import com.anastas.carsshop.model.Authority;

public class AuthorityMapper {

	 public static AuthorityDTO toAuthorityDTO(Authority entity) {
	        return new AuthorityDTO(
	        		entity.getId(),
	                entity.getAuthority()
	        );
	    }
}
