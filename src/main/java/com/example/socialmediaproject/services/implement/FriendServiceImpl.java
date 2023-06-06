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
import java.util.function.Function;
import java.util.function.Predicate;

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
        HashSet<Friends> partnerFriendList = getFriendList(partnerId);
        HashSet<Friends> userFriendList = getFriendList(userId);
        HashSet<Friends> shorterFriendList = (userFriendList.size() < partnerFriendList.size()) ? userFriendList : partnerFriendList;
        HashSet<Friends> longerFriendList = (userFriendList.size() > partnerFriendList.size()) ? userFriendList : partnerFriendList;
        List<Friends> result = new ArrayList<>();

        shorterFriendList.forEach(friends -> {
            if(!longerFriendList.add(friends)){
                result.add(friends);
            }
        });

        return result;
    }

    @Override
    public int countMutualFriend(String userId, String partnerId) {
        return 0;
    }


}
