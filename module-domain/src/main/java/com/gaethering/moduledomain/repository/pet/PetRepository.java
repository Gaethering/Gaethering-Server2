package com.gaethering.moduledomain.repository.pet;

import com.gaethering.moduledomain.domain.member.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
