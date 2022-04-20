package com.westernacher.test.service;

import com.westernacher.test.controller.request.SortUserDetailsRequest;
import com.westernacher.test.controller.request.UserRequest;
import com.westernacher.test.exception.UserNotFoundException;
import com.westernacher.test.model.SortUserDetails;
import com.westernacher.test.model.User;
import com.westernacher.test.model.entity.UserEntity;
import com.westernacher.test.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void createUser() {
        when(userRepository.save(any())).thenReturn(getUserEntity());

        final UserRequest userRequest = getUserRequest();
        final User createdUser = userService.createUser(userRequest);

        assertThat(createdUser).isNotNull();
        Assertions.assertEquals(userRequest.getFirstName(), createdUser.getFirstName());
        Assertions.assertEquals(userRequest.getLastName(), createdUser.getLastName());
        Assertions.assertEquals(userRequest.getEmail(), createdUser.getEmail());
        Assertions.assertEquals(userRequest.getBirthDate(), createdUser.getBirthDate());
    }

    @Test
    void updateUser() throws UserNotFoundException {
        when(userRepository.findById(any())).thenReturn(Optional.of(getUserEntity()));
        when(userRepository.save(any())).thenReturn(getUserEntity().setLastName("Sidorov"));

        final UserRequest userRequest = getUserRequest();
        userRequest.setLastName("Sidorov");
        final User user = userService.updateUser(1L, userRequest);

        assertThat(user).isNotNull();
        Assertions.assertEquals(userRequest.getFirstName(), user.getFirstName());
        Assertions.assertEquals(userRequest.getLastName(), user.getLastName());
        Assertions.assertEquals(userRequest.getEmail(), user.getEmail());
        Assertions.assertEquals(userRequest.getBirthDate(), user.getBirthDate());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void findUserById() throws UserNotFoundException {
        when(userRepository.save(any())).thenReturn(getUserEntity());
        when(userRepository.findById(any())).thenReturn(Optional.of(getUserEntity()));

        final UserRequest userRequest = getUserRequest();
        final User createdUser = userService.createUser(userRequest);
        final User user = userService.findUserById(createdUser.getId());

        Assertions.assertEquals(createdUser.getId(), user.getId());
        Assertions.assertEquals(createdUser.getFirstName(), user.getFirstName());
        Assertions.assertEquals(createdUser.getLastName(), user.getLastName());
        Assertions.assertEquals(createdUser.getEmail(), user.getEmail());
        Assertions.assertEquals(createdUser.getBirthDate(), user.getBirthDate());
    }

    @Test
    void findAllUsers() {
        final List<UserEntity> userEntityList = getUserEntityList();
        when(userRepository.findAll((Sort) any())).thenReturn(userEntityList);
        final List<User> userList = userService
                .findAllUsers(new SortUserDetailsRequest().setSortUserDetails(SortUserDetails.SORT_BY_EMAIL_ASC));

        assertThat(userList).isNotNull();
        Assertions.assertEquals(userEntityList.get(0).getFirstName(), userList.get(0).getFirstName());
        Assertions.assertEquals(userEntityList.get(0).getLastName(), userList.get(0).getLastName());
        Assertions.assertEquals(userEntityList.get(0).getEmail(), userList.get(0).getEmail());
        Assertions.assertEquals(userEntityList.get(0).getBirthDate(), userList.get(0).getBirthDate());

        Assertions.assertEquals(userEntityList.get(1).getFirstName(), userList.get(1).getFirstName());
        Assertions.assertEquals(userEntityList.get(1).getLastName(), userList.get(1).getLastName());
        Assertions.assertEquals(userEntityList.get(1).getEmail(), userList.get(1).getEmail());
        Assertions.assertEquals(userEntityList.get(1).getBirthDate(), userList.get(1).getBirthDate());
    }

    @Test
    void deleteUser() throws UserNotFoundException {
        when(userRepository.findById(any())).thenReturn(Optional.of(getUserEntity()));

        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    private UserEntity getUserEntity() {
        return new UserEntity()
                .setId(1)
                .setFirstName("Ivan")
                .setLastName("Ivanov")
                .setEmail("ivanov@yandex.ru")
                .setBirthDate(new Date(1970, Calendar.MAY, 28));
    }

    private UserRequest getUserRequest() {
        return new UserRequest()
                .setFirstName("Ivan")
                .setLastName("Ivanov")
                .setEmail("ivanov@yandex.ru")
                .setBirthDate(new Date(1970, Calendar.MAY, 28));
    }

    private List<UserEntity> getUserEntityList() {
        return List.of(
                new UserEntity()
                        .setId(1)
                        .setFirstName("Ivan")
                        .setLastName("Kozlov")
                        .setEmail("akozlov@yandex.ru")
                        .setBirthDate(new Date(1970, Calendar.MAY, 28)),
                new UserEntity()
                        .setId(1)
                        .setFirstName("Oleg")
                        .setLastName("Petrov")
                        .setEmail("bpetrov@yandex.ru")
                        .setBirthDate(new Date(1985, Calendar.APRIL, 15))
        );
    }
}