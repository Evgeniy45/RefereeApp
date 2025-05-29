package com.basketball.refereeapp.service;

import com.basketball.refereeapp.model.Role;
import com.basketball.refereeapp.model.User;
import com.basketball.refereeapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(
                new User("user1", "pass", Role.REFEREE)
        ));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("user1", users.get(0).getUsername());
    }

    @Test
    void testGetByUsername_UserExists() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(
                new User("admin", "pass", Role.ADMIN)
        ));

        User user = userService.getByUsername("admin");

        assertNotNull(user);
        assertEquals("admin", user.getUsername());
    }

    @Test
    void testGetByUsername_UserNotFound() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.getByUsername("ghost"));

        assertEquals("User not found", exception.getMessage());
    }
}
