package com.parentapp.auth.service;

import com.parentapp.auth.model.Role;
import com.parentapp.auth.model.User;
import com.parentapp.auth.model.VerificationToken;
import com.parentapp.auth.repository.RoleRepository;
import com.parentapp.auth.repository.UserRepository;
import com.parentapp.auth.repository.VerificationTokenRepository;
import com.parentapp.parent.Parent;
import com.parentapp.parent.ParentRepository;
import com.parentapp.users.UserDTO;
import com.parentapp.util.NotFoundException;
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
    private final VerificationTokenRepository tokenRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, ParentRepository parentRepository, VerificationTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.parentRepository = parentRepository;
        this.tokenRepository = tokenRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<UserDTO> findAllAsDTO() {
        return findAll().stream()
                .map((user) -> mapToDTO(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    @Transactional
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
    public VerificationToken createVerificationTokenForUser(final String userId, final String token) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
        return myToken;
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
        userDTO.setEnabled(user.isEnabled());
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
        user.setEnabled(userDTO.isEnabled());
        return user;
    }

    public boolean idExists(final String id) {
        return userRepository.existsByIdIgnoreCase(id);
    }

    public boolean existsByUsername(final String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

}
