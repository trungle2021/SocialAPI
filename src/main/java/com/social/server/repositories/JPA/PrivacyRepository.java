package com.social.server.repositories.JPA;

import com.social.server.dtos.PrivaciesDTO;
import com.social.server.entities.Privacies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivacyRepository extends JpaRepository<Privacies,String> {
    Optional<Privacies> findByPrivacyType(String type);
}
