package com.example.socialmediaproject.services.implement;

import com.example.socialmediaproject.entities.Friends;
import com.example.socialmediaproject.repositories.FriendRepository;
import com.example.socialmediaproject.services.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;




    @Override
    public HashSet<Friends> getFriendList(String userId) {
        return friendRepository.getFriendList(userId);
    }

    @Override
    public List<Friends> getMutualFriend(String userId, String partnerId) {
        List<Friends> mutualFriendList = new ArrayList<>();
        HashSet<Friends> userFriendList = getFriendList(userId);
        HashSet<Friends> partnerFriendList = getFriendList(partnerId);
        HashSet<Friends> shorterList = userFriendList.size() < partnerFriendList.size() ? userFriendList : partnerFriendList;
        for (int i = 0;i< shorterList.size();i++)
        {
            boolean isMutualFriend = userFriendList.add(shorterList.stream().fin(x->x.getUserFriendId()));
            if(isMutualFriend){
                mutualFriendList.add((Friends)shorterList.toArray()[i]);
            }
        }
        return mutualFriendList;
    }

    @Override
    public int countMutualFriend(String userId, String partnerId) {
        return 0;
    }
}
