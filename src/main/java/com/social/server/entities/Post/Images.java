package com.social.server.entities.Post;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue(value = "IMAGE")
public class Images extends PostBase{
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "order_image")
    private Integer orderImage;
    @Column(name = "privacy_id")
    private String privacyId;
}
