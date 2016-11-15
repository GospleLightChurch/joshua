package org.gyt.web.repository.repository;

import org.gyt.web.model.Role;
import org.gyt.web.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("From User u where :role member u.roles")
    Page<User> findByRole(Pageable pageable, @Param("role") Role role);
}
