package com.social.server.services.implement;

import com.social.server.dtos.FriendDTO;
import com.social.server.dtos.FriendListDTO;
import com.social.server.dtos.MutualFriendDTO;
import com.social.server.entities.User.FriendRequest;
import com.social.server.entities.User.Friends;
import com.social.server.entities.User.Users;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final KafkaTemplate kafkaTemplate;



    @Override
    public FriendListDTO getFriendListByStatus(String userId, String status) {
        List<FriendDTO> friendList = friendRepository.getFriendListByStatus(userId,status);

        if(friendList.isEmpty()){
            return null;
        }
        List<FriendDTO> friendDTOList = friendList.stream()
                .map(user-> EntityMapper.mapToDto(user,FriendDTO.class))
                .peek(item -> item.setMutualFriend(getMutualFriend(userId, item.getId())))
                .toList();
        return  FriendListDTO.builder()
                .friendList(friendDTOList)
                .friendListOwnerId(userId)
                .numberOfFriend(friendDTOList.size())
                .build();

    }


    @Override
    public MutualFriendDTO getMutualFriend(String userId, String partnerId) {
       List<Users> mutualFriends = friendRepository.getMutualFriend(userId,partnerId);

       if(mutualFriends.isEmpty()){
           return null;
       }
        List<FriendDTO> friendDTOList = mutualFriends
                .stream()
                .map(user -> EntityMapper.mapToDto(user, FriendDTO.class))
                .toList();


        return MutualFriendDTO.builder()
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
        Friends friends = friendRepository.findByUserIdAndUserFriendId(userId,userFriendId);
        if(friends.getFriendStatus().equals(pendingStatus) && friends.getFriendStatus().equals(acceptStatus)){
            FriendRequest friendRequestUpdated = updateFriendStatus(friendRequest);
            kafkaTemplate.send("friend-acceptances",friendRequestUpdated);
        }
    }
    @Override
    public void sendFriendRequest(FriendRequest friendRequest){
        FriendRequest friendRequestInserted = createFriendRequest(friendRequest);
        kafkaTemplate.send("friend-requests",friendRequestInserted);

    }

    public FriendRequest createFriendRequest(FriendRequest friendRequest) {
        String userId = friendRequest.getUserId();
        String friendId = friendRequest.getUserFriendId();
        String pendingStatus = FriendStatus.PENDING.toString();
        String acceptStatus = FriendStatus.ACCEPTED.toString();
        if(userId.equals(friendId)){
            throw new SocialAppException(HttpStatus.BAD_REQUEST,"ID DUPLICATE");
        }

        Friends friends = friendRepository.findByUserIdAndUserFriendId(userId,friendId);
        if(friends.getFriendStatus().equals(acceptStatus)){
            throw new SocialAppException(HttpStatus.BAD_REQUEST,"USERS ALREADY FRIENDS");
        }

        if(friendRequest.getFriendStatus().equals(pendingStatus)){
            Friends request = Friends.builder()
                    .userId(userId)
                    .userFriendId(friendId)
                    .establishAt(Instant.now())
                    .friendStatus(pendingStatus)
                    .build();
            return EntityMapper.mapToDto(friendRepository.save(request), FriendRequest.class);
        }
        throw new SocialAppException(HttpStatus.BAD_REQUEST,"To create, Friend Request Status must be PENDING ");
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
