package br.com.zup.orangetalents.mercadolivre.produto.service;

import org.springframework.stereotype.Component;

@Component
public class FakeEnviadorEmail implements EnviadorEmail {

	@Override
	public void enviar(String destinatario, String corpo) {
		System.out.println("================================");
		System.out.println("Destinatário: " + destinatario);
		System.out.println("Mensagem: " + corpo);
		System.out.println("================================");
	}
}
