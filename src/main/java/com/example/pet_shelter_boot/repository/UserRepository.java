package com.example.pet_shelter_boot.repository;

import com.example.pet_shelter_boot.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(Long id);

    @Query("SELECT u from UserEntity u")
    @EntityGraph(attributePaths = "pets")
    List<UserEntity> findAllWithPets();

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE users
            SET name = CASE WHEN :name IS NOT NULL THEN :name ELSE name END,
            email = CASE WHEN :email IS NOT NULL THEN :email ELSE email END,
            age = CASE WHEN :age IS NOT NULL THEN :age ELSE age END
            WHERE id = :id
            """, nativeQuery = true)
    void updateUser(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("email") String email,
            @Param("age") Integer age
    );

}
