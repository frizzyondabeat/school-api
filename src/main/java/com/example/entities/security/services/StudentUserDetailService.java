package com.example.entities.security.services;

import com.example.entities.exceptions.ApiNotFoundException;
import com.example.entities.models.Student;
import com.example.entities.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
@Service
public class StudentUserDetailService implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (!studentRepository.existsByEmailAddress(email)) {
            log.error("User [{}] is not in the database", email);
            throw new ApiNotFoundException("User not found in database");
        }
        log.info("User [{}] was found", email);
        Student user = studentRepository.findByEmailAddress(email);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(roles -> authorities.add(new SimpleGrantedAuthority(roles.getRole())));
        return new User(user.getEmailAddress(), user.getPassword(), authorities);
    }
}
