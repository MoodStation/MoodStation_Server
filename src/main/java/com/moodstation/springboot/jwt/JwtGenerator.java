package com.moodstation.springboot.jwt;

import com.moodstation.springboot.repository.UserRepository;
import com.moodstation.springboot.service.JwtUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtGenerator {

    public static final long JWT_ACCESS_TOKEN_VALIDITY = 60 * 60 * 24 * 365 * 10; //10년
    public static final long JWT_REFRESH_TOKEN_VALIDITY = 24 * 60 * 60 * 180; //6개월
    private final JwtUserDetailsService jwtUserDetailsService;
    private final UserRepository userRepository;
//    @Autowired
//    private JwtUserDetailsService jwtUserDetailsService;

//    @Autowired
//    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //generate token for user
    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> li = new ArrayList<>();
        for (GrantedAuthority a: userDetails.getAuthorities()) {
            li.add(a.getAuthority());
        }
        claims.put("id",userRepository.findUserByEmail(userDetails.getUsername()).getId());
        claims.put("role",li.get(0));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public String generateRefreshToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(this.getUserIdFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //JWT 토큰으로부터 userId 인증
    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public Map<String, Object> getUserParseInfo(String token) {
        Claims parseInfo = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        Map<String, Object> result = new HashMap<>();
        result.put("userId", parseInfo.getSubject());
        result.put("role", parseInfo.get("role", String.class));
        return result;
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Request의 Header에서 token 값을 가져옵니다. "ACCESS-TOKEN" : 토큰값
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("ACCESS-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
