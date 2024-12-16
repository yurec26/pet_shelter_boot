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
    @Query("""
            UPDATE PetEntity p
            SET p.name =:name,
                p.userId =:userId
            WHERE p.id =:id
            """)
    void updatePet(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("userId") Long userId
    );
}
