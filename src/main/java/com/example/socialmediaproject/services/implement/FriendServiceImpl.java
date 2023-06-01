package com.example.socialmediaproject.services.implement;

import com.example.socialmediaproject.entities.Friends;
import com.example.socialmediaproject.repositories.FriendRepository;
import com.example.socialmediaproject.services.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    @Override
    public List<Friends> getFriendList(String userId) {
        return friendRepository.getFriendList(userId);
    }

    @Override
    public List<Friends> getMutalFriend(String userId, String partnerId) {
//        Set<Friends> userFriendList = getFriendList(userId);
        return null;
    }

    @Override
    public int countMutalFriend(String userId, String partnerId) {
        return 0;
    }
}
