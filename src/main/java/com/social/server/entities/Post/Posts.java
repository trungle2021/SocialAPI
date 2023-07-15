package com.social.server.entities.Post;

import com.social.server.entities.Post.PostBase;
import com.social.server.entities.Privacies;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue(value = "POST")
public class Posts extends PostBase {
    @Column(name = "privacy_id")
    public String privacyStatus;
}
