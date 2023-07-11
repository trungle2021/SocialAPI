package com.social.server.repositories;

import com.social.server.dtos.PrivaciesDTO;
import com.social.server.entities.Privacies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivacyRepository extends JpaRepository<Privacies,String> {
    PrivaciesDTO findByPrivacyType(String type);
}
