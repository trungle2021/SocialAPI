package com.social.server.services.Post.PostBase;

import com.social.server.entities.Post.PostBase;

import java.util.List;

public interface PostBaseService {
    void deleteChild(String childId);
    <T> void deleteAll(List<T> children);

}
