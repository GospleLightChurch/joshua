package org.gyt.web.repository.repository;

import org.gyt.web.model.Fellowship;
import org.gyt.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FellowshipRepository extends JpaRepository<Fellowship, String> {

    List<Fellowship> findAllByOrderByName();

    @Query("select f from Fellowship f where f.owner = :username or :username member f.admins")
    List<Fellowship> findByUser(@Param("username") User username);
}
