package com.example.pet_shelter_boot.repository;

import com.example.pet_shelter_boot.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PetRepository extends JpaRepository<PetEntity, Long> {

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE pets
            SET name = CASE WHEN :name IS NOT NULL THEN :name ELSE name END,
            user_id = CASE WHEN :userId IS NOT NULL THEN :userId ELSE user_id END
            WHERE id = :id
            """, nativeQuery = true)
    void updatePet(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("userId") Long userId
    );
}
