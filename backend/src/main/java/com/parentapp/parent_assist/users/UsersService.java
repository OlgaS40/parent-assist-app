package com.parentapp.parent_assist.users;

import com.parentapp.parent_assist.parents.Parents;
import com.parentapp.parent_assist.parents.ParentsRepository;
import com.parentapp.parent_assist.roles.Roles;
import com.parentapp.parent_assist.roles.RolesRepository;
import com.parentapp.parent_assist.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final ParentsRepository parentsRepository;

    public UsersService(final UsersRepository usersRepository,
            final RolesRepository rolesRepository, final ParentsRepository parentsRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.parentsRepository = parentsRepository;
    }

    public List<UsersDTO> findAll() {
        final List<Users> userss = usersRepository.findAll(Sort.by("id"));
        return userss.stream()
                .map((users) -> mapToDTO(users, new UsersDTO()))
                .collect(Collectors.toList());
    }

    public UsersDTO get(final String id) {
        return usersRepository.findById(id)
                .map(users -> mapToDTO(users, new UsersDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final UsersDTO usersDTO) {
        final Users users = new Users();
        mapToEntity(usersDTO, users);
        users.setId(usersDTO.getId());
        return usersRepository.save(users).getId();
    }

    public void update(final String id, final UsersDTO usersDTO) {
        final Users users = usersRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usersDTO, users);
        usersRepository.save(users);
    }

    public void delete(final String id) {
        usersRepository.deleteById(id);
    }

    private UsersDTO mapToDTO(final Users users, final UsersDTO usersDTO) {
        usersDTO.setId(users.getId());
        usersDTO.setUsername(users.getUsername());
        usersDTO.setEmail(users.getEmail());
        usersDTO.setPassword(users.getPassword());
        usersDTO.setParent(users.getParent());
        usersDTO.setPersoninfo(users.getPersoninfo());
        usersDTO.setUserRoless(users.getUserRolesRoless() == null ? null : users.getUserRolesRoless().stream()
                .map(roles -> roles.getId())
                .collect(Collectors.toList()));
        usersDTO.setParentId(users.getParentId() == null ? null : users.getParentId().getId());
        return usersDTO;
    }

    private Users mapToEntity(final UsersDTO usersDTO, final Users users) {
        users.setUsername(usersDTO.getUsername());
        users.setEmail(usersDTO.getEmail());
        users.setPassword(usersDTO.getPassword());
        users.setParent(usersDTO.getParent());
        users.setPersoninfo(usersDTO.getPersoninfo());
        final List<Roles> userRoless = rolesRepository.findAllById(
                usersDTO.getUserRoless() == null ? Collections.emptyList() : usersDTO.getUserRoless());
        if (userRoless.size() != (usersDTO.getUserRoless() == null ? 0 : usersDTO.getUserRoless().size())) {
            throw new NotFoundException("one of userRoless not found");
        }
        users.setUserRolesRoless(userRoless.stream().collect(Collectors.toSet()));
        final Parents parentId = usersDTO.getParentId() == null ? null : parentsRepository.findById(usersDTO.getParentId())
                .orElseThrow(() -> new NotFoundException("parentId not found"));
        users.setParentId(parentId);
        return users;
    }

    public boolean idExists(final String id) {
        return usersRepository.existsByIdIgnoreCase(id);
    }

}
