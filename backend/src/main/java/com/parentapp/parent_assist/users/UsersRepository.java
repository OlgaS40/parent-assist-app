package com.parentapp.parent_assist.users;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepository extends JpaRepository<Users, String> {

    boolean existsByIdIgnoreCase(String id);

}
