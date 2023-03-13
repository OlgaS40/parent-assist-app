package com.parentapp.backend.users;

import com.parentapp.backend.auth.model.Role;
import com.parentapp.backend.auth.model.User;
import com.parentapp.backend.auth.repository.RoleRepository;
import com.parentapp.backend.auth.repository.UserRepository;
import com.parentapp.backend.parent.Parent;
import com.parentapp.backend.parent.ParentRepository;
import com.parentapp.backend.util.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ParentRepository parentRepository;

    public UserService(final UserRepository userRepository,
                       final RoleRepository roleRepository, final ParentRepository parentRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.parentRepository = parentRepository;
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<UserDTO> findAllAsDTO() {
        return findAll().stream()
                .map((user) -> mapToDTO(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    public UserDTO get(final String id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }
    @Transactional
    public String create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        user.setId(userDTO.getId());
        return userRepository.save(user).getId();
    }
    @Transactional
    public void update(final String id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }
    @Transactional
    public void delete(final String id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setParentId(user.getParent() == null ? null : user.getParent().getId());
        userDTO.setUserRole(user.getUserRole() == null ? null : user.getUserRole().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        userDTO.setParentId(user.getParent() == null ? null : user.getParent().getId());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        final List<Role> userRoles =
                userDTO.getUserRole() == null ? Collections.emptyList() : userDTO.getUserRole().stream()
                        .map(roleName -> roleRepository.findByName(roleName).orElseThrow(NotFoundException::new))
                        .toList();
        if (userRoles.size() != (userDTO.getUserRole() == null ? 0 : userDTO.getUserRole().size())) {
            throw new NotFoundException("one of userRoles not found");
        }
        user.setUserRole(new HashSet<>(userRoles));
        final Parent parentId = userDTO.getParentId() == null ? null : parentRepository.findById(userDTO.getParentId())
                .orElseThrow(() -> new NotFoundException("parentId not found"));
        user.setParent(parentId);
        return user;
    }

    public boolean idExists(final String id) {
        return userRepository.existsByIdIgnoreCase(id);
    }

}
