package com.thincbackend.service;

import com.thincbackend.domain.User;
import com.thincbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService{
    private final UserRepository userRepository;

    public User saveUser(User user){
        validateDuplicateUser(user);

        return userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        User findUser = userRepository.findByUserID(user.getUserID());
        if(findUser!=null){
            throw new IllegalStateException("이미 가입된 아이디입니다.");
        }
    }

    public User loadUserByUserId(String ID){
        User user = userRepository.findByUserID(ID);

        return User.builder()
                .UserID(user.getUserID())
                .Password(user.getPassword())
                .Nickname(user.getNickname())
                .build();
    }
}
