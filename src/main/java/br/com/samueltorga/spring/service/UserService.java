package br.com.samueltorga.spring.service;

import br.com.samueltorga.spring.controller.dto.user.NewUserRequest;
import br.com.samueltorga.spring.controller.dto.user.UserResponse;
import br.com.samueltorga.spring.mappers.UserMapper;
import br.com.samueltorga.spring.repository.UserRepository;
import br.com.samueltorga.spring.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(user -> userMapper.toUserResponse(user, user.getUserDetail()));
    }

    public Optional<UserResponse> getUserById(Integer id) {
        return userRepository.findById(id).map(user -> userMapper.toUserResponse(user, user.getUserDetail()));
    }

    public UserResponse createUser(NewUserRequest newUserRequest) {
        User userEntity = userMapper.toUserEntity(newUserRequest);
        userMapper.referenceUserDetailWithUser(userEntity, userEntity.getUserDetail());
        userEntity.setPassword(passwordEncoder.encode(newUserRequest.getPassword()));
        User saved = userRepository.save(userEntity);
        return userMapper.toUserResponse(saved, saved.getUserDetail());
    }

    public boolean deleteUser(Integer id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }
}