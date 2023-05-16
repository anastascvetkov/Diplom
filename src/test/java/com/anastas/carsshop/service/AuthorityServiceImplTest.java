package com.anastas.carsshop.service;

import com.anastas.carsshop.repository.UserRepository;
import com.anastas.carsshop.excaption.ElementNotFoundException;
import com.anastas.carsshop.model.Authority;
import com.anastas.carsshop.model.User;
import com.anastas.carsshop.repository.AuthorityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@Transactional
@SpringBootTest
public class AuthorityServiceImplTest {

    @Autowired
    private AuthorityServiceImpl authorityService;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private UserRepository userRepository;


    @Test()
    public void givenNullUsernameWhenSetAuthorityThenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,
                () -> authorityService.setAuthorityToUser(null, "ROLE_ADMIN"));
    }
    @Test()
    public void givenBlankUsernameWhenSetAuthorityThenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,
                () -> authorityService.setAuthorityToUser("", "ROLE_ADMIN"));
    }
    @Test()
    public void givenNullAuthorityWhenSetAuthorityThenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,
                () -> authorityService.setAuthorityToUser("Test", null));
    }
    @Test()
    public void givenBlankAuthorityWhenSetAuthorityThenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,
                () -> authorityService.setAuthorityToUser("Test", ""));
    }
    @Test()
    public void givenNotStartWithROLE_AuthorityWhenSetAuthorityThenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class,
                () -> authorityService.setAuthorityToUser("Test", "sth"));
    }

    @Test()
    public void givenUsernameThatNotExistWhenSetAuthorityThenThrowElementNotFound(){
         given(userRepository.findByUsername("test")).willThrow(ElementNotFoundException.class);
        assertThrows(ElementNotFoundException.class,
                () -> authorityService.setAuthorityToUser("test", "ROLE_ADMIN"));
    }

    @Test()
    public void givenAuthorityThatIsAlreadyGrantedThatNotExistWhenSetAuthorityThenThrowElementNotFound(){
        User user = new User(1L, "test","test");
        Authority authority = new Authority(1L, "ROLE_ADMIN", user);

        given(authorityRepository.findByAuthority("ROLE_ADMIN")).willReturn(Optional.of(authority));
        given(userRepository.findByUsername("test")).willReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class,
                () -> authorityService.setAuthorityToUser("test", "ROLE_ADMIN"));
    }


}
