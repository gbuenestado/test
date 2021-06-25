package es.bnext.exception;

public class ContactNotExist extends Exception {

	private static final long serialVersionUID = -8254186128519242978L;

	public ContactNotExist() {
		super();
	}

	public ContactNotExist(String message, Throwable cause) {
		super(message, cause);
	}

	public ContactNotExist(String message) {
		super(message);
	}

	public ContactNotExist(Throwable cause) {
		super(cause);
	}

}
