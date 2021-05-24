package br.com.zup.orangetalents.mercadolivre.usuario.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zup.orangetalents.mercadolivre.comum.service.HashService;
import br.com.zup.orangetalents.mercadolivre.usuario.model.Usuario;

@Service
public class UsuarioService {

	@PersistenceContext
	private EntityManager entityManager;
	private HashService hashService;
	
	public UsuarioService(HashService hashService) {
		this.hashService = hashService;
	}
	
	@Transactional
	public void salvar(Usuario usuario) {
		usuario.setSenha(hashService.gerarHash(usuario.getSenha()));
		entityManager.persist(usuario);
	}
}
