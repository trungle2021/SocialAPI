package com.social.server.entities.Post;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(value = "SHARE_POST")
@SuperBuilder
public class SharedPosts extends PostBase{

    @Basic
    @Column(name = "shared_at", nullable = true)
    private Instant sharedAt;

}
