package com.voltx.evgenee.repository;

import com.voltx.evgenee.entity.EvUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvUserRepository extends JpaRepository<EvUser, Long> {

    @Query("SELECT eu FROM EvUser eu WHERE eu.authUser.email = :email")
    Optional<EvUser> findByEmail(@Param("email") String email);
}
