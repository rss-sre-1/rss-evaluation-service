package com.revature.exceptions;

public class EvaluationException extends RuntimeException{

	private static final long serialVersionUID = 334013825789322843L;

	public EvaluationException() {
		super();
	}

	public EvaluationException(String message) {
		super(message);
	}

	public EvaluationException(Throwable cause) {
		super(cause);
	}

	public EvaluationException(String message, Throwable cause) {
		super(message, cause);
	}
}
