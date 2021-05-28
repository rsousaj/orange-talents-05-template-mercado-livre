package br.com.zup.orangetalents.mercadolivre.usuario.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class GerenciadorTokenImpl implements GerenciadorToken {
	@Value("${mercadolivre.jwt.secret}")
	private String secret;
	
	@Value("${mercadolivre.jwt.expiration}")
	private String expiration;
	
	@Value("${mercadolivre.descricao}")
	private String issuer;

	public String gerar(Authentication auth) {
		Date agora = new Date();
		Date expiracao = new Date(agora.getTime() + Long.parseLong(this.expiration));
		
		return Jwts.builder()
			.setIssuer(this.issuer)
			.setIssuedAt(agora)
			.setSubject(auth.getName())
			.setExpiration(expiracao)
			.signWith(SignatureAlgorithm.HS256, this.secret)
			.compact();
	}

	@Override
	public boolean isValid(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getIdentificadorUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
}
