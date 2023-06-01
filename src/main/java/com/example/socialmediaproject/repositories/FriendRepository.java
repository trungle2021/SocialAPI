package com.example.socialmediaproject.repositories;

import com.example.socialmediaproject.entities.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public interface FriendRepository extends JpaRepository<Friends,String> {
    @Query("Select f from Friends f where f.userId = :userId")
    HashSet<Friends> getFriendList(String userId);
}
