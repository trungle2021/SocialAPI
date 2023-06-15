package com.example.socialmediaproject.repositories;

import com.example.socialmediaproject.entities.Friends;
import com.example.socialmediaproject.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friends,String> {
    @Query("Select u from Friends f Join Users u on u.id = f.userFriendId where f.userId = :userId")
    List<Users> getFriendList(String userId);

    @Query("""
        SELECT
                    U
                FROM
                    Users AS U
                WHERE
                    EXISTS( SELECT
                            1
                        FROM
                            Friends AS F
                        WHERE
                            F.userId = :userId
                                AND EXISTS( SELECT
                                    1
                                FROM
                                    Friends AS F1
                                WHERE
                                    F.userFriendId = F1.userFriendId
                                        AND F1.userId = :partnerId)
                                AND F.userFriendId = U.id)
        """)
    List<Users> getMutualFriend(String userId, String partnerId);
}


