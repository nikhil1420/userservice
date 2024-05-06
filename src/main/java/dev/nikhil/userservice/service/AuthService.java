package dev.nikhil.userservice.service;

import dev.nikhil.userservice.dtos.UserDto;
import dev.nikhil.userservice.exception.UserAlreadyExistsException;
import dev.nikhil.userservice.exception.UserDoseNotExistsException;
import dev.nikhil.userservice.models.Session;
import dev.nikhil.userservice.models.SessionStatus;
import dev.nikhil.userservice.models.User;
import dev.nikhil.userservice.repository.SessionRepository;
import dev.nikhil.userservice.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserDto signUp(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isEmpty()) {
            throw new UserAlreadyExistsException("User with " +email + " already exits.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        UserDto userDto = UserDto.from(savedUser);
        return userDto;
    }

    public ResponseEntity<UserDto> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UserDoseNotExistsException("User with " +email + " does not exist.");
        }

        User user = userOptional.get();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong username password");
        }

        String token = RandomStringUtils.randomAlphanumeric(30);

        UserDto userDto = UserDto.from(user);


        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH-TOKEN:" , token);

        Session session = new Session();
        session.setStatus(SessionStatus.ACTIVE);
        session.setUser(user);
        session.setToken(token);
        sessionRepository.save(session);

        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);

        return response;
    }

    public ResponseEntity<Void> logout(String token, UUID userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return null;
        }

        Session session = sessionOptional.get();

        session.setStatus(SessionStatus.LOGGED_OUT);

        sessionRepository.save(session);

        return ResponseEntity.ok().build();
    }

    public Optional<UserDto> validate(String token, UUID userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);
        if (sessionOptional.isEmpty()) {
            return Optional.empty();
        }

        Session session = sessionOptional.get();
        if (session.getStatus() != SessionStatus.ACTIVE) {
            return Optional.empty();
        }

        User user = userRepository.findById(userId).orElse(null);
        UserDto userDto = UserDto.from(user);

//        if(!(session.getExpiresAt() > new Date()))
//        {
//            return SessionStatus.EXPIRED;
//        }
        return Optional.of(userDto);
    }


}
