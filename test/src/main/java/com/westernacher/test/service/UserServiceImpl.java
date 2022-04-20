package com.westernacher.test.service;

import com.westernacher.test.controller.request.SortUserDetailsRequest;
import com.westernacher.test.controller.request.UserRequest;
import com.westernacher.test.exception.UserNotFoundException;
import com.westernacher.test.model.User;
import com.westernacher.test.model.entity.UserEntity;
import com.westernacher.test.repository.UserRepository;
import com.westernacher.test.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.westernacher.test.utils.UserUtils.mapUserEntityToUser;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(final UserRequest userRequest) {
        return mapUserEntityToUser(
                userRepository.save(new UserEntity()
                        .setFirstName(userRequest.getFirstName())
                        .setLastName(userRequest.getLastName())
                        .setEmail(userRequest.getEmail())
                        .setBirthDate(userRequest.getBirthDate()))
        );
    }

    @Override
    public User updateUser(final long userId, final UserRequest userRequest) throws UserNotFoundException {
        final UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User id = %s not found.", userId)));

        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setBirthDate(userRequest.getBirthDate());

        return mapUserEntityToUser(userRepository.save(userEntity));
    }

    @Override
    public User findUserById(final long userId) throws UserNotFoundException {
        return mapUserEntityToUser(
                userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException(String.format("User id = %s not found.", userId)))
        );
    }

    @Override
    public List<User> findAllUsers(final SortUserDetailsRequest sortUserDetails) {
        final List<UserEntity> userEntityList = userRepository
                .findAll(sortUserDetails.getSortUserDetails().getSort());

        return userEntityList
                .stream()
                .map(UserUtils::mapUserEntityToUser)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(final long userId) throws UserNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User id = %s not found.", userId)));

        userRepository.deleteById(userId);
    }
}
