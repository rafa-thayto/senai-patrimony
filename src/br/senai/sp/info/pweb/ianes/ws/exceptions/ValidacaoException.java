package br.senai.sp.info.pweb.ianes.ws.exceptions;

public class ValidacaoException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidacaoException() {
		super();
	}

	public ValidacaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ValidacaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidacaoException(String message) {
		super(message);
	}

	public ValidacaoException(Throwable cause) {
		super(cause);
	}

}
