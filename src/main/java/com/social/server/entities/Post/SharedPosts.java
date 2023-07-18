package com.social.server.entities.Post;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(value = "SHARE_POST")
@SuperBuilder
public class SharedPosts extends PostBase{

}
