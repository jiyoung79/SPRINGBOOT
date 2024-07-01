package com.example.app.config.auth.logoutHandler;

import com.example.app.config.auth.PrincipalDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
//    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String KAKAO_CLIENT_ID = "c170a57532e152fe524d695e7b4b6b44";
//    @Value("${spring.security.oauth2.client.kakao.logout.redirect.uri}")
    private String KAKAO_LOGOUT_REDIRECT_URI = "http://localhost:8080/login";

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("CustomLogoutSuccessHandler's onLogoutSuccess()");
        System.out.println("KAKAO_CLIENT_ID : " + KAKAO_CLIENT_ID);
        System.out.println("KAKAO_LOGOUT_REDIRECT_URI : " + KAKAO_LOGOUT_REDIRECT_URI);

        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        String provider = principalDetails.getUserDto().getProvider();

        // Kakao Server Disconn
        if (provider!=null && "kakao".equals(provider)) {
            response.sendRedirect("https://kauth.kakao.com/oauth/logout?client_id=" + KAKAO_CLIENT_ID + "&logout_redirect_uri=" + KAKAO_LOGOUT_REDIRECT_URI);
            return;

        } else if (provider!=null && "naver".equals(provider)) {
            response.sendRedirect("https://nid.naver.com/nidlogin.logout?returl=https://www.naver.com/");
            return;

        } else if (provider!=null && "google".equals(provider)) {
            response.sendRedirect("http://accounts.google.com/Logout");
            return;
        }

        response.sendRedirect("/");
    }
}