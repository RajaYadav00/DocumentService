package com.pdf.generate.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchElementException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public NoSuchElementException(String message) {
		super(message);
	}
}
