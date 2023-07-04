package com.social.server.repositories;

import com.social.server.entities.Friends;
import com.social.server.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friends,String> {
    @Query("Select u from Friends f Join Users u on u.id = f.userFriendId where f.userId = :userId and f.friendStatus = :status")
    List<Users> getFriendListByStatus(String userId,String status);

    // This query selects user's profile  which are mutual friend in two sub-queries of two user in Friends table, from Users Table
    @Query("""
        select U from Users as U where exists (
             select f1.userFriendId from Friends as f1
                JOIN
             Friends as f2
                ON f1.userFriendId = f2.userFriendId
                where f1.userId = :userId and f2.userId = :partnerId and f1.userFriendId = U.id)
        """)
    List<Users> getMutualFriend(String userId, String partnerId);
}


