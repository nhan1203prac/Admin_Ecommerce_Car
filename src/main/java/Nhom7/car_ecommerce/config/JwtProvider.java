package Nhom7.car_ecommerce.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
public class JwtProvider {
	public static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public static String GenerateToken(Authentication auth) {	
		Collection<? extends GrantedAuthority> listAuthority = auth.getAuthorities();
		String roles = populateAuthorities(listAuthority);
//		String roles = listAuthority.stream()
//	            .map(GrantedAuthority::getAuthority)
//	            .collect(Collectors.joining(","));
	    System.out.printf("roles: %s%n", roles);
//		System.out.printf("role", roles);
		String jwt = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+864000000))
				.claim("email", auth.getName())
				.claim("authorities",roles)
				.signWith(key)
				.compact();
		return jwt;
				
				
	}
	
	public static String getEmaiFromToken(String token) {
		token = token.substring(7);
		Claims claim = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		String email = String.valueOf(claim.get("email"));
		return email;
	}

	private static String populateAuthorities(Collection<? extends GrantedAuthority> listAuthority) {
		// TODO Auto-generated method stub
		Set<String> auth = new HashSet<String>();
		for (GrantedAuthority grantedAuthority : listAuthority) {
			auth.add(grantedAuthority.getAuthority());
		}
		return String.join(",", auth);
	}
}
