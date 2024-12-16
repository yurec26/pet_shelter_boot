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
    @Query("""
            UPDATE UserEntity u
            SET u.name =:name,
                u.email =:email,
                u.age =:age
            WHERE u.id =:id
            """)
    void updateUser(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("email") String email,
            @Param("age") Integer age
    );

    @Transactional
    @Modifying
    @Query("""
            UPDATE PetEntity p
            SET p.userId = Null
            WHERE p.userId = :id
            """)
    void deleteUserFromPets(Long id);
}
