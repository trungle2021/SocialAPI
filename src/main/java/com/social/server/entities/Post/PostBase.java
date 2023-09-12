package com.social.server.entities.Post;


import com.social.server.entities.Privacies;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table (name = "post_base")
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
    @Column(name = "owner")
    protected String owner;
    @Column(name = "parent_id")
    protected String parentId;
    @Column(name="type",insertable = false,updatable = false)
    protected String type;
    @Column(name = "is_deleted")
    protected Boolean isDeleted;
    @Column(name = "posted_at")
    protected Instant postedAt;
    @Column(name = "updated_at")
    protected Instant updatedAt;
}
