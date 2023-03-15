package com.parentapp.services;

import com.parentapp.auth.model.URole;
import com.parentapp.auth.model.User;
import com.parentapp.auth.repository.RoleRepository;
import com.parentapp.auth.repository.UserRepository;
import com.parentapp.fixture.RoleFixture;
import com.parentapp.fixture.UserDTOFixture;
import com.parentapp.fixture.UserFixture;
import com.parentapp.users.UserDTO;
import com.parentapp.users.UserService;
import com.parentapp.util.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserService userService;
    private User testUser;
    private UserDTO testUserDTO;
    private List<User> testUsers;
    private List<UserDTO> testUserDTOs;

    @BeforeEach
    public void setup() {
        testUser = UserFixture.testUser();
        testUserDTO = UserDTOFixture.testUserDTO();
        testUsers = UserFixture.testUserList();
        testUserDTOs = UserDTOFixture.userDTOList();
    }
    @Test
    void findAll_whenInvoked_returnsAListWithTwoUsers() {
        when(userRepository.findAll()).thenReturn(testUsers);
        Assertions.assertThat(userService.findAll())
                .isNotEmpty()
                .containsExactlyInAnyOrderElementsOf(testUsers);
    }
    @Test
    void findAllAsDTO_whenInvoked_returnsAListWithTwoUserDTOs() {
        when(userRepository.findAll()).thenReturn(testUsers);

        assertThat(userService.findAllAsDTO()).hasSize(2);
        assertThat(userService.findAllAsDTO()).containsExactlyInAnyOrderElementsOf(testUserDTOs);
    }
    @Test
    void get_UserDTOId_returnsUser() {
        when(userRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(testUser));

        UserDTO user = userService.get("test01");
        assertThat(user).isNotNull();
        assertThat(user).isEqualTo(testUserDTO);
    }
    @Test
    void get_NonExistentId_throwNotFoundException() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        String id = testUser.getId();

        assertThrows(NotFoundException.class, () -> userService.get(id));
        verify(userRepository, times(1)).findById(id);
    }
    @Test
    void create_whenInvoked_returnsUserId() {
        when(roleRepository.findByName(URole.USER)).thenReturn(Optional.ofNullable(RoleFixture.userRole));
        when(roleRepository.findByName(URole.CONTENT_MAKER)).thenReturn(Optional.ofNullable(RoleFixture.contentMakerRole));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        String createdUserId = userService.create(testUserDTO);

        verify(userRepository, times(1)).save(any(User.class));
        assertThat(createdUserId).isNotNull();
        assertThat(createdUserId).isEqualTo(testUser.getId());
    }
    @Test
    void update_userIdAndUserDTO_updatedUser() {
        UserDTO updatedUserDTO = testUserDTO;
        String id = updatedUserDTO.getId();
        testUserDTO.setUserRole(Set.of(URole.USER, URole.CONTENT_MAKER));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(testUser));

        User updatedUser = testUser;
        updatedUser.setUserRole(Set.of(RoleFixture.userRole, RoleFixture.contentMakerRole));
        when(roleRepository.findByName(URole.USER)).thenReturn(Optional.of(RoleFixture.userRole));
        when(roleRepository.findByName(URole.CONTENT_MAKER)).thenReturn(Optional.of(RoleFixture.contentMakerRole));


        userService.update(id, updatedUserDTO);

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(updatedUser);
    }
    @Test
    void update_NonExistentUser_throwsNotFoundException() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        UserDTO updatedUserDTO = testUserDTO;
        String id = updatedUserDTO.getId();
        User updatedUser = testUser;

        assertThrows(NotFoundException.class, () -> userService.update(id, updatedUserDTO));

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).save(updatedUser);
    }
    @Test
    void delete_userId_deletedUser() {
        userService.delete(testUser.getId());
        verify(userRepository, times(1)).deleteById(testUser.getId());
    }
}
