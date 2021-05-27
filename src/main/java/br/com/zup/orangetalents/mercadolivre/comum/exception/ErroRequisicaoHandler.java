package br.com.zup.orangetalents.mercadolivre.comum.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@RestControllerAdvice
public class ErroRequisicaoHandler extends ResponseEntityExceptionHandler {

	private MessageSource messageSource;

	public ErroRequisicaoHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Erro> erros = new ArrayList<>();

		ex.getGlobalErrors().forEach(globalError -> {
			erros.add(new Erro(globalError.getDefaultMessage()));
		});

		ex.getFieldErrors().forEach(fieldError -> {
			String campo = fieldError.getField();
			String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

			erros.add(new Erro(campo, mensagem));
		});

		return new ResponseEntity<Object>(erros, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		return new ResponseEntity<Object>(new Erro("A requisição HTTP não pode ser processada."), status);
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Erro> handleResponseStatusException(ResponseStatusException ex) {
		return ResponseEntity.status(ex.getStatus()).body(new Erro(ex.getReason()));
	}
}

class Erro {

	@JsonInclude(value = Include.NON_NULL)
	private String campo;
	private String mensagem;

	public Erro(String mensagem) {
		this.mensagem = mensagem;
	}

	public Erro(String campo, String mensagem) {
		super();
		this.campo = campo;
		this.mensagem = mensagem;
	}

	public String getCampo() {
		return campo;
	}

	public String getMensagem() {
		return mensagem;
	}
}
