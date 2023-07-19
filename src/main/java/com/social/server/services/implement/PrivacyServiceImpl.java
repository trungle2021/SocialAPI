package com.social.server.services.implement;

import com.social.server.dtos.PrivaciesDTO;
import com.social.server.entities.Privacies;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.exceptions.SocialAppException;
import com.social.server.repositories.PrivacyRepository;
import com.social.server.services.PrivacyService;
import com.social.server.utils.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrivacyServiceImpl implements PrivacyService {
    private final PrivacyRepository privacyRepository;
    @Override
    public List<PrivaciesDTO> getPrivacies() {
        return privacyRepository.findAll().stream().map(item -> EntityMapper.mapToDto(item,PrivaciesDTO.class)).toList();
    }

    @Override
    public PrivaciesDTO findById(String id) {
        if(id == null){
            throw new ResourceNotFoundException("Privacy Type not found","id",id);
        }
        return EntityMapper.mapToDto(privacyRepository.findById(id),PrivaciesDTO.class);
    }

    @Override
    public PrivaciesDTO findByType(String type) {
        return privacyRepository.findByPrivacyType(type).orElseThrow(() -> new SocialAppException(HttpStatus.BAD_REQUEST, "Privacy Type not exists"));
    }

    @Override
    public PrivaciesDTO create(PrivaciesDTO privaciesDTO) {
        Privacies privacies = EntityMapper.mapToEntity(privaciesDTO, Privacies.class);
        return EntityMapper.mapToDto(privacyRepository.save(privacies), PrivaciesDTO.class);
    }

    @Override
    public PrivaciesDTO update(PrivaciesDTO privaciesDTO) {
        Privacies privacies = EntityMapper.mapToEntity(privaciesDTO, Privacies.class);
        return EntityMapper.mapToDto(privacyRepository.save(privacies), PrivaciesDTO.class);
    }

    @Override
    public String delete(String id) {
        privacyRepository.deleteById(id);
        return "Delete privacy successfully" ;
    }
}
