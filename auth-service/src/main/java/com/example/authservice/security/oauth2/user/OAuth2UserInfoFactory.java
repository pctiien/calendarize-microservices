package com.example.authservice.security.oauth2.user;

import com.example.authservice.entity.AuthProvider;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String,Object> attributes)
    {
        if(registrationId.equalsIgnoreCase(AuthProvider.github.toString()))
        {
            return new GithubOauth2UserInfo(attributes);
        }
        else{
            throw new OAuth2AuthenticationException("Login with "+registrationId +" is not supported yet");
        }
    }
}
