package com.example.authservice.service.userService;

import com.example.authservice.dto.UserDto;
import com.example.authservice.entity.User;
import com.example.authservice.exception.ResourceNotFoundException;
import com.example.authservice.exception.UserNotFoundException;
import com.example.authservice.mapper.UserMapper;
import com.example.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDto updateUser(UserDto dto) {
        Optional<User> optionalAppUser = userRepository.findUserByEmail(dto.getEmail());
        if(optionalAppUser.isEmpty()) {
            throw new UserNotFoundException("email",dto.getEmail());
        }
        User user = UserMapper.toEntity(dto);
        user.setId(optionalAppUser.get().getId());
        userRepository.save(user);
        return dto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("id",userId.toString()));
        return UserMapper.toDto(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User","email",email));
    }

    @Override
    public List<User> getUserByEmail(List<String> emails) {
        if(emails == null)
        {
            return getAllUsers();
        }
        return userRepository.findAllByEmailIn(emails).stream()
                .map(user -> user.orElseThrow(() ->
                        new ResourceNotFoundException("user", "email", "[Không tìm thấy email]")))
                .toList();
    }
}
