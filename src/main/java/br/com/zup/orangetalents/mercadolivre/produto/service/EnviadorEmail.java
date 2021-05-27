package br.com.zup.orangetalents.mercadolivre.produto.service;

public interface EnviadorEmail {

	void enviar(String destinatario, String corpo);
}
