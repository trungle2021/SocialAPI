package com.social.server.repositories;

import com.social.server.dtos.FriendDTO;
import com.social.server.entities.User.Friends;
import com.social.server.entities.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friends,String> {
    String getFriendsByStatus = """
            select new com.social.server.dtos.FriendDTO(u.id,
            u.firstName,
            u.lastName,
            u.accountId,
            u.isDeleted,
            ()
            ) from Users u where u.id IN
             (select u.userFriendId from Friends u where u.userId = :userId and u.friendStatus = :status
             UNION
              select u1.userId from Friends u1 where  u1.userFriendId = :userId and u1.friendStatus = :status)
            """;
    @Query(getFriendsByStatus)
    List<FriendDTO> getFriendListByStatus(String userId, String status);

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

    Friends findByUserIdAndUserFriendId(String userFriendId, String userFriendId1);
}


