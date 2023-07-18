package com.social.server.services;

import com.social.server.entities.Post.PostBase;

import java.util.List;

public interface PostBaseService {
    void deleteChild(String childId);
    <T> void deleteAll(List<T> children);
}
