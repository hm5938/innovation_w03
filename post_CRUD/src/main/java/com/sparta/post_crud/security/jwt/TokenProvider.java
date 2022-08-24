package com.sparta.post_crud.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import com.sparta.post_crud.UserDetailsServiceImpl;
import com.sparta.post_crud.dto.ResponseDto;
import com.sparta.post_crud.dto.TokenDto;
import com.sparta.post_crud.entity.RefreshToken;
import com.sparta.post_crud.entity.User;
import com.sparta.post_crud.repository.RefreshTokenRepository;
import com.sparta.post_crud.repository.UserRepository;
import io.jsonwebtoken.MalformedJwtException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;
@Component
public class TokenProvider{
    private static final int SEC = 1;
    private static final int MINUTE = 60 * SEC;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

//    public static final String CLAIM_EXPIRED_DATE = "EXPIRED_DATE";
//    public static final String CLAIM_USER_NAME = "USER_NAME";
    public static final String CLAIM_AUTHORITIES_KEY = "AUTHORITIES";
    public static final String JWT_SECRET = "jwt_secret_!@#$%";
    private static final String BEARER_PREFIX = "";

    // JWT 토큰의 유효기간: 7일 (단위: milliseconds)
    private static final Long REFRECH_TOKEN_EXPIRE_TIME = (long) (1000 * 7 * DAY);
    private static final Long ACCESS_TOKEN_EXPIRE_TIME = (long) (1000 * 5 * MINUTE);

//    private final SecretKey key;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public TokenProvider(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
    }



    public TokenDto generateTokenDto(User user) {
        long now = new Date().getTime();

        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(accessTokenExpiresIn)
                .sign(generateAlgorithm());


//                Jwts.builder()
//                .setSubject(user.getUsername())
////                .claim(CLAIM_AUTHORITIES_KEY,AUTHORITY.ROLE_MEMBER.toString())
//                .setExpiration(accessTokenExpiresIn)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
        String refreshToken =     JWT.create()
                .withClaim("USER",user.getId())
                .withExpiresAt(new Date(now + REFRECH_TOKEN_EXPIRE_TIME))
                .sign(generateAlgorithm());
//                Jwts.builder()
//                .setExpiration(new Date(now + REFRECH_TOKEN_EXPIRE_TIME))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();

        RefreshToken refreshTokenObject = new RefreshToken(user.getId(),refreshToken);
        refreshTokenRepository.save(refreshTokenObject);

        return TokenDto.builder()
                .grantType(BEARER_PREFIX)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpriesIn(accessTokenExpiresIn.getTime())
                .build();
    }

    public String generateAccessToken(String token){
        DecodedJWT jwt = null;
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .build();

            jwt = verifier.verify(token);

        User user = userRepository.findById(jwt.getClaim("USER").asLong()).orElseThrow();
        long now = new Date().getTime();

        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(accessTokenExpiresIn)
                .sign(generateAlgorithm());
        return accessToken;
    }

    public boolean validateToken(String token){
        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .build();

            jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
//            log.error(e.getMessage());
            return false;
        }
    }

    public ResponseDto<?> deleteRefreshToken(User user){
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(user.getId());
        if(refreshToken.isEmpty()) return ResponseDto.fail("NOT_FIND_USER","유효한 토큰이 없습니다");
        else {
            refreshTokenRepository.deleteById(user.getId());
            return ResponseDto.success(user);
        }
    }

    public User getUserFromAuthentication(String token){
        String name = this.decodeUsername(token);
       User user = userRepository.findByUsername(name).orElseThrow(()-> new RuntimeException("사용자 없음"));
       return user;
    }

    //JwtTokenProvider.java
    public Authentication getAuthentication(String token) {
        try {
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(this.decodeUsername(token));
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } catch (MalformedJwtException ex) {
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request){
        String accessToken = request.getHeader("Access-Token");
        String refreshToken = request.getHeader("Refresh-Token");
        DecodedJWT decodedJWT = isValidToken(accessToken)
                .orElseThrow(() -> new IllegalArgumentException("유효한 토큰이 아닙니다."));
        Date expiredDate = decodedJWT
                .getClaim(CLAIM_AUTHORITIES_KEY)
                .asDate();
        Date now = new Date();
        if (expiredDate.before(now)) {
            throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
        }
        String name = decodedJWT.getSubject();
        return name;
    }

    public RefreshToken isPresentRefreshToken(User user){
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(user.getId());
        if(refreshToken.isEmpty()) return null;
        else return refreshToken.get();
    }

        private Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(JWT_SECRET);
    }


    private Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .build();

            jwt = verifier.verify(token);
        } catch (Exception e) {
//            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }

    public String decodeUsername(String token){
        DecodedJWT decodedJWT = isValidToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효한 토큰이 아닙니다."));
        Date expiredDate = decodedJWT
                .getExpiresAt();
        Date now = new Date();
        if (expiredDate.before(now)) {
            throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
        }
        String name = decodedJWT.getSubject();
        return name;
    }
}
