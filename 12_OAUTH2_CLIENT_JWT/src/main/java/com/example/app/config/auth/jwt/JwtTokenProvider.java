package com.example.app.config.auth.jwt;


import com.example.app.config.auth.PrincipalDetails;
import com.example.app.domain.dto.UserDto;
import com.example.app.domain.repository.SignatureRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {


//    @Autowired
//    private SignatureRepository signatureRepository;

    String url="jdbc:mysql://localhost:3306/bookDb";
    String username ="root";
    String password = "12345678";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    //Key 저장
    private Key key;

    public JwtTokenProvider() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,username,password);
            pstmt = conn.prepareStatement("select * from signature");
            rs = pstmt.executeQuery();

            if(rs.next()){
                byte[] keyBytes = rs.getBytes("signkey");
                this.key = Keys.hmacShaKeyFor(keyBytes);
            }else{
                byte[] keyBytes = KeyGenerator.getKeygen();
                this.key = Keys.hmacShaKeyFor(keyBytes);
                //DB INSERT
                pstmt=conn.prepareStatement("insert into signature values(?,now())");
                pstmt.setBytes(1,keyBytes);
                pstmt.execute();
            }
            System.out.println("JwtTokenProvider Constructor  Key init: " + key);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public TokenInfo generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        UserDto userDto = principalDetails.getUserDto();
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + 60*1000); // 60초후 만료
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("username",authentication.getName())             //정보저장
                .claim("auth", authorities)                             //정보저장
                .claim("principal", authentication.getPrincipal())      //정보저장
                .claim("credentials", authentication.getCredentials())  //정보저장
                .claim("details", authentication.getDetails())          //정보저장
                .claim("provider", userDto.getProvider())               //정보저장
                .claim("accessToken", principalDetails.getAccessToken())//정보저장
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 24 * 60 * 60 * 1000))    //1일: 24 * 60 * 60 * 1000 = 86400000
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("JwtTokenProvider.generateToken.accessToken : " + accessToken);
        System.out.println("JwtTokenProvider.generateToken.refreshToken : " + refreshToken);

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(auth -> new SimpleGrantedAuthority(auth))
                        .collect(Collectors.toList());

        String username = claims.getSubject(); //username

        //JWT Added
//        .out.println("[JWTTOKENPROVIDER] principalDetails  : " + claims.get("principal"));

        String provider =  (String)claims.get("provider");
        String password = (String)claims.get("password");
        String auth = (String)claims.get("auth");
        String oauthAccessToken = (String)claims.get("accessToken");
        UserDto userDto = new UserDto();
        userDto.setProvider(provider);
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setRole(auth);

        PrincipalDetails principalDetails = new PrincipalDetails();
        principalDetails.setUserDto(userDto);
        principalDetails.setAccessToken(oauthAccessToken);   //Oauth AccessToken
//        .out.println("[JWTTOKENPROVIDER] getAuthentication() principalDetails  : " + principalDetails);

        //JWT + NO REMEMBERME
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(principalDetails, claims.get("credentials"), authorities);
        return usernamePasswordAuthenticationToken;

    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
//        }
//        catch (ExpiredJwtException e) {
//            log.info("Expired JWT Token", e);

        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }
}