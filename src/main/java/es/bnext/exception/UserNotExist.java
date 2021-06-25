package es.bnext.exception;

public class UserNotExist extends Exception {

	private static final long serialVersionUID = -2841998804694054656L;

	public UserNotExist() {
		super();
	}

	public UserNotExist(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotExist(String message) {
		super(message);
	}

	public UserNotExist(Throwable cause) {
		super(cause);
	}

}
