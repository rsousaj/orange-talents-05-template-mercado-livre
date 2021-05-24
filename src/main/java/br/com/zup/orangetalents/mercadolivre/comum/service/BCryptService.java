package br.com.zup.orangetalents.mercadolivre.comum.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class BCryptService implements HashService {

	public String gerarHash(String value) {
		return BCrypt.hashpw(value, BCrypt.gensalt());
	}
}
