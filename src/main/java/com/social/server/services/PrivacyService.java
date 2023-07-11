package com.social.server.services;

import com.social.server.dtos.PrivaciesDTO;

import java.util.List;

public interface PrivacyService {
    List<PrivaciesDTO> getPrivacies();
    PrivaciesDTO findById(String id);
    PrivaciesDTO findByType(String type);
    PrivaciesDTO create(PrivaciesDTO privaciesDTO);
    PrivaciesDTO update(PrivaciesDTO privaciesDTO);
    String delete(String id);
}
