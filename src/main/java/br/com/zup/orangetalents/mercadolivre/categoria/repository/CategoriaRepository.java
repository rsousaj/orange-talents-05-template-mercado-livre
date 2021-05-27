package br.com.zup.orangetalents.mercadolivre.categoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.orangetalents.mercadolivre.categoria.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
