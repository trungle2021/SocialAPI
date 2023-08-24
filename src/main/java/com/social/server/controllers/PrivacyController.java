package com.social.server.controllers;

import com.social.server.dtos.PrivaciesDTO;
import com.social.server.services.Privacy.PrivacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/privacy")
public class PrivacyController {
    private final PrivacyService privacyService;
    @GetMapping
    public ResponseEntity<List<PrivaciesDTO>> getPrivacies(){
        return ResponseEntity.ok(privacyService.getPrivacies());
    }
    @PostMapping
    public ResponseEntity<PrivaciesDTO> create(@RequestBody PrivaciesDTO privaciesDTO){
        return ResponseEntity.ok(privacyService.create(privaciesDTO));
    }
    @PutMapping
    public ResponseEntity<PrivaciesDTO> update(@RequestBody PrivaciesDTO privaciesDTO){
        return ResponseEntity.ok(privacyService.update(privaciesDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        return ResponseEntity.ok(privacyService.delete(id));
    }
}
