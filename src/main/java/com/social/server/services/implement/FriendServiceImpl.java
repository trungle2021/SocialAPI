package com.social.server.services.implement;

import com.social.server.dtos.FriendDTO;
import com.social.server.dtos.FriendListDTO;
import com.social.server.dtos.MutualFriendDTO;
import com.social.server.entities.User.FriendRequest;
import com.social.server.entities.User.Friends;
import com.social.server.exceptions.FriendRequestException;
import com.social.server.exceptions.SocialAppException;
import com.social.server.repositories.FriendRepository;
import com.social.server.services.FriendService;
import com.social.server.utils.EntityMapper;
import com.social.server.utils.FriendStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final KafkaTemplate kafkaTemplate;

    @Override
    public FriendListDTO getFriendListByStatus(String userId, String status) {
        List<FriendDTO> friendList = friendRepository.getFriendListAndMutualFriendByStatus(userId, status);


        List<FriendDTO> friendDTOList = friendList.stream()
                .map(user -> EntityMapper.mapToDto(user, FriendDTO.class))
                .toList();
        return friendList.isEmpty() ?
                FriendListDTO.builder()
                        .friendList(Collections.emptyList())
                        .friendListOwnerId(userId)
                        .numberOfFriend(0)
                        .build()
                :
                FriendListDTO.builder()
                        .friendList(friendDTOList)
                        .friendListOwnerId(userId)
                        .numberOfFriend(friendDTOList.size())
                        .build();
    }


    @Override
    public MutualFriendDTO getMutualFriend(String userId, String partnerId) {
        List<FriendDTO> mutualFriends = friendRepository.getMutualFriend(userId, partnerId);

        List<FriendDTO> friendDTOList = mutualFriends
                .stream()
                .map(user -> EntityMapper.mapToDto(user, FriendDTO.class))
                .toList();

        return mutualFriends.isEmpty() ?
                MutualFriendDTO.builder()
                        .mutualFriendList(Collections.emptyList())
                        .ownerId(userId)
                        .partnerId(partnerId)
                        .numberOfMutualFriend(0)
                        .build()
                :
                MutualFriendDTO.builder()
                        .mutualFriendList(friendDTOList)
                        .ownerId(userId)
                        .partnerId(partnerId)
                        .numberOfMutualFriend(friendDTOList.size())
                        .build();


    }

    @Override
    public void acceptFriendRequest(FriendRequest friendRequest) {
        String userId = friendRequest.getUserId();
        String userFriendId = friendRequest.getUserFriendId();
        String acceptStatus = FriendStatus.ACCEPTED.toString();
        String pendingStatus = FriendStatus.PENDING.toString();
        Friends friends = friendRepository.findByUserIdAndUserFriendId(userId, userFriendId);


        if (friends.getFriendStatus().equals(pendingStatus) && friendRequest.getFriendStatus().equals(acceptStatus)) {

            FriendRequest friendRequestUpdated = updateFriendStatus(friendRequest);
            //if a user who accepts the friend request
            // then insert 1 record into database to present the relationship bi-direction
            friendRequest.setFriendStatus(acceptStatus);
            createFriendRequest(friendRequest);
            kafkaTemplate.send("friend-acceptances", friendRequestUpdated);
        }
    }

    @Override
    public void sendFriendRequest(FriendRequest friendRequest) {
        FriendRequest friendRequestInserted = createFriendRequest(friendRequest);
        kafkaTemplate.send("friend-requests", friendRequestInserted);

    }

    public FriendRequest createFriendRequest(FriendRequest friendRequest) {
        String userId = friendRequest.getUserId();
        String friendId = friendRequest.getUserFriendId();
        String acceptStatus = FriendStatus.ACCEPTED.toString();
        String pendingStatus = FriendStatus.PENDING.toString();


        if (userId.equals(friendId)) {
            throw new SocialAppException(HttpStatus.BAD_REQUEST, "User ID and Friend ID must be different");
        }
        //check if the user1 and user2 has already existed
        // else user1 just send request only

        Friends friends = friendRepository.findByUserIdAndUserFriendId(userId, friendId);
        if (friends != null) {
            boolean hasAcceptedStatus = friends.getFriendStatus().equals(acceptStatus);
            boolean hasPendingStatus = friends.getFriendStatus().equals(pendingStatus);
            if (hasAcceptedStatus) {
                throw new FriendRequestException("Users are already friends");
            } else if (hasPendingStatus) {
                throw new FriendRequestException("Friend request already sent");
            }
        } else {
            if (friendRequest.getFriendStatus().equals(pendingStatus)) {
                Friends request = Friends.builder()
                        .userId(userId)
                        .userFriendId(friendId)
                        .establishAt(Instant.now())
                        .friendStatus(pendingStatus)
                        .build();
                return EntityMapper.mapToDto(friendRepository.save(request), FriendRequest.class);
            } else {
                throw new SocialAppException(HttpStatus.BAD_REQUEST, "Friend request status must be PENDING ");
            }
        }
        return null;
    }

    public FriendRequest updateFriendStatus(FriendRequest friendRequest) {
        Friends request = Friends.builder()
                .userId(friendRequest.getUserId())
                .userFriendId(friendRequest.getUserFriendId())
                .establishAt(Instant.now())
                .friendStatus(friendRequest.getFriendStatus())
                .build();
        return EntityMapper.mapToDto(friendRepository.save(request), FriendRequest.class);
    }


}
