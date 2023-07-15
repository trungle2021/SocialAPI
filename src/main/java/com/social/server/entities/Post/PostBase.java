package com.social.server.entities.Post;


import com.social.server.entities.Privacies;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Entity
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class PostBase {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    protected String id;
    @Column(name = "content")
    protected String content;
    @Column(name = "content")
    protected String owner;
    @Column(name = "parent_id")
    protected String parentId;
    @Column(name = "parent_type")
    protected String parentType;
    @Column(name = "is_deleted")
    protected Boolean isDeleted;
    @Column(name = "posted_at")
    protected Timestamp postedAt;
    @Column(name = "updated_at")
    protected Timestamp updatedAt;
//    @Column(name = "privacy")
//    @ManyToOne
//    public Privacies privacyStatus;

}
