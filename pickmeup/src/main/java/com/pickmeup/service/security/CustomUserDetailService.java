package com.pickmeup.service.security;


import com.pickmeup.advice.CUserNotFoundException;
import com.pickmeup.domain.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public UserDetails loadUserByUsername(String email) {
            return usersRepository.findByEmail(email).orElseThrow(CUserNotFoundException::new);

    }

}
