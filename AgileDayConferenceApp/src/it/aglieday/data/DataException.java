package it.aglieday.data;

public class DataException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataException() {
		super();
	}

	public DataException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public DataException(String detailMessage) {
		super(detailMessage);
	}

	public DataException(Throwable throwable) {
		super(throwable);
	}
}
