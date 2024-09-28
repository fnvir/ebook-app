package com.app.ebook.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Service
public class JwtUtil {
	@Value("${jwt.secret}")
	private String secretKey;
	@Value("${jwt.expiration}")
	private long expirationInterval;
	@Value("${jwt.refresh.expiration}")
	private long refreshExpirationInterval;
	@Value("${spring.application.name}")
	private String issuer;
	
	private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
		return JWT.create()
				.withSubject(userDetails.getUsername())
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis()+expirationInterval))
				.withIssuer(issuer)
				.withPayload(extraClaims)
				.sign(Algorithm.HMAC256(secretKey));
	}
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return buildToken(extraClaims, userDetails, expirationInterval);
	}

	public String generateRefreshToken(UserDetails userDetails) {
		return buildToken(new HashMap<>(), userDetails, refreshExpirationInterval);
	}
	
	public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
				.withIssuer(issuer)
				.build();
		DecodedJWT jwt = verifier.verify(token);
		return jwt.getSubject();
	}
	
}
