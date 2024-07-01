package com.example.app.config.auth;

import com.example.app.domain.UserDto;
import com.example.app.entity.User;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOption = userRepository.findById(username);

        if (userOption.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        // Entity를 프론트로 가져오려면 Dto 사용
        UserDto userDto = new UserDto();
        User user = userOption.get();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());

        return new PrincipalDetails(userDto);
    }
}
