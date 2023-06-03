package com.selyuto.termbase.repositories;

import com.selyuto.termbase.models.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    @Query(value = "SELECT p.id, p.name, p.date_created, p.date_last_modified FROM privileges p JOIN users_to_privileges utp ON p.id = utp.privilege_id AND utp.user_id = :userId", nativeQuery = true)
    Collection<Privilege> getPrivilegesByUserId(@Param("userId") Long userId);
}
