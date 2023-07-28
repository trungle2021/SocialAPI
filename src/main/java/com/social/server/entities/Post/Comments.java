package com.social.server.entities.Post;

import com.social.server.dtos.CommentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;


//@SqlResultSetMapping(name="CommentDTOMapping",classes = {
//        @ConstructorResult(targetClass = CommentDTO.class,
//                columns = {
//                        @ColumnResult(name = "id",type = String.class),
//                        @ColumnResult(name = "postId",type = String.class),
//                        @ColumnResult(name = "content",type = String.class),
//                        @ColumnResult(name = "imageUrl",type = String.class),
//                        @ColumnResult(name = "owner",type = String.class),
//                        @ColumnResult(name = "parentId",type = String.class),
//                        @ColumnResult(name = "likeCount",type = Integer.class),
//                        @ColumnResult(name = "isDeleted",type = Boolean.class),
//                        @ColumnResult(name = "postedAt",type = Instant.class),
//                        @ColumnResult(name = "updatedAt",type = Instant.class),
//                        @ColumnResult(name = "childCommentCount",type = Integer.class)
//                })
//})


@Entity
@DiscriminatorValue(value = "COMMENT")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Comments extends PostBase {
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "post_id")
    private String postId;
}
