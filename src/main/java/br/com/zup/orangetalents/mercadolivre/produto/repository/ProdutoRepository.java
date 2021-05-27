package br.com.zup.orangetalents.mercadolivre.produto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.orangetalents.mercadolivre.produto.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
