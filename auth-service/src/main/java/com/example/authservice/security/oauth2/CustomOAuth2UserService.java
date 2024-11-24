package com.example.authservice.security.oauth2;

import com.example.authservice.email.EmailSubject;
import com.example.authservice.email.EmailDetails;
import com.example.authservice.entity.AuthProvider;
import com.example.authservice.entity.User;
import com.example.authservice.exception.OAuth2AuthenticationProcessingException;
import com.example.authservice.rabbitmq.RabbitMQProducer;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.UserPrincipal;
import com.example.authservice.security.oauth2.user.OAuth2UserInfo;
import com.example.authservice.security.oauth2.user.OAuth2UserInfoFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RabbitMQProducer rabbitMQProducer;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }
    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                userRequest.getClientRegistration().getRegistrationId(),oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail()))
        {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user = new User();
        if(userOptional.isPresent())
        {
            user = userOptional.get();
            if(!user.getAuthProvider().equals(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId())))
            {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getAuthProvider() + " account. Please use your " + user.getAuthProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user,oAuth2UserInfo);
        }
        else{

            EmailDetails emailDetails = new EmailDetails(user.getEmail(),user.getName(), EmailSubject.WELCOME_SUBJECT,null);
            rabbitMQProducer.sendEmail(emailDetails);

            user = userRepository.save(registerNewUser(userRequest,oAuth2UserInfo));
        }
        return UserPrincipal.create(user,oAuth2User.getAttributes());
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

    private User registerNewUser(OAuth2UserRequest userRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user =  User.builder().authProvider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()))
                .email(oAuth2UserInfo.getEmail())
                .name(oAuth2UserInfo.getName())
                .imageUrl(oAuth2UserInfo.getImageUrl()).build();
        return userRepository.save(user);
    }
}
