package com.social.server.repositories;

import com.social.server.dtos.FriendDTO;
import com.social.server.entities.User.Friends;
import com.social.server.entities.User.Users;
import com.social.server.utils.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friends, String> {

    @Query("""
            SELECT new com.social.server.dtos.FriendDTO(
                           f.userFriendId,
                           u.firstName,
                           u.lastName,
                           u.accountId,
                           u.isDeleted,
                           (SELECT
                               COUNT(*) as mutual_friend_count
                           FROM
                               Friends AS f1
                           JOIN
                               Friends AS f2
                                   ON f1.userFriendId = f2.userFriendId
                                   AND f1.friendStatus = f2.friendStatus
                           WHERE
                               f1.userId = f.userId
                               AND f2.userId = f.userFriendId
                               AND f1.friendStatus = 'ACCEPTED')
                               )
                       FROM
                           Friends AS f
                       JOIN
                           Users AS u
                               ON f.userFriendId = u.id
                       WHERE
                           f.userId = :userId
                           AND f.friendStatus = :status
            """)
    // If status is active, it's mean this query retrieve friend list, otherwise (pending status) it just returns friend requests list.
    List<FriendDTO> getFriendListAndMutualFriendByStatus(String userId, String status);

    // This query selects user's profile  which are mutual friend in two sub-queries of two user in Friends table, from Users Table
    @Query("""
              SELECT new com.social.server.dtos.FriendDTO(
                                u.id,
                                u.firstName,
                                u.lastName,
                                u.accountId,
                                u.isDeleted)
                              FROM
                                  Users AS u
                              WHERE
                                  EXISTS (
                                      SELECT f1.userFriendId FROM
                                          Friends f1
                                      JOIN
                                          Friends f2
                                              ON f1.userFriendId = f2.userFriendId
                                              AND f1.userFriendId = u.id
                                              AND f1.friendStatus = f2.friendStatus
                                      WHERE
                                          f1.userId = :userId
                                          AND f2.userId = :partnerId
                                          AND f1.friendStatus = 'ACCEPTED')
            """)
    List<FriendDTO> getMutualFriend(String userId, String partnerId);

    Friends findByUserIdAndUserFriendId(String userId, String userFriendId);
}


