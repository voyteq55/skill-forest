package com.wburda.skillforest.backend.services;

import com.wburda.skillforest.backend.entities.User;
import com.wburda.skillforest.backend.repositories.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String googleSub = oAuth2User.getAttribute("sub");
        String googleUsername = oAuth2User.getAttribute("name");

        Optional<User> existingUser = userRepository.findByGoogleSub(googleSub);
        if (existingUser.isEmpty()) {
            User user = new User();
            user.setName(googleUsername);
            user.setGoogleSub(googleSub);
            userRepository.save(user);
        }

        return oAuth2User;
    }
}
