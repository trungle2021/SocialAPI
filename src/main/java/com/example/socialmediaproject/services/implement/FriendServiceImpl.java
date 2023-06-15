package com.example.socialmediaproject.services.implement;

import com.example.socialmediaproject.dtos.FriendDTO;
import com.example.socialmediaproject.dtos.FriendListDTO;
import com.example.socialmediaproject.dtos.MutualFriendDTO;
import com.example.socialmediaproject.entities.Friends;
import com.example.socialmediaproject.entities.Users;
import com.example.socialmediaproject.repositories.FriendRepository;
import com.example.socialmediaproject.repositories.UserRepository;
import com.example.socialmediaproject.services.FriendService;
import com.example.socialmediaproject.utils.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;


    @Override
    public FriendListDTO getFriendList(String userId) {
        List<Users> friendList = friendRepository.getFriendList(userId);

        if(friendList.isEmpty()){
            return null;
        }
        List<FriendDTO> friendDTOList = friendList.stream()
                .map(user->EntityMapper.mapToDto(user,FriendDTO.class))
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
