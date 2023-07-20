package com.social.server.services.implement;

import com.social.server.dtos.FriendDTO;
import com.social.server.dtos.FriendListDTO;
import com.social.server.dtos.MutualFriendDTO;
import com.social.server.entities.User.Users;
import com.social.server.repositories.FriendRepository;
import com.social.server.services.FriendService;
import com.social.server.utils.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;


    @Override
    public FriendListDTO getFriendListByStatus(String userId, String status) {
        List<Users> friendList = friendRepository.getFriendListByStatus(userId,status);

        if(friendList.isEmpty()){
            return null;
        }
        List<FriendDTO> friendDTOList = friendList.stream()
                .map(user-> EntityMapper.mapToDto(user,FriendDTO.class))
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

}
