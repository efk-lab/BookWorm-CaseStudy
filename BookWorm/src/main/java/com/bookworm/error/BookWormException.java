package com.bookworm.error;

public class BookWormException extends RuntimeException {

	private static final long serialVersionUID = 3560654528167410058L;
	
	public BookWormException(String exception) {
        super(exception);
    }

}
