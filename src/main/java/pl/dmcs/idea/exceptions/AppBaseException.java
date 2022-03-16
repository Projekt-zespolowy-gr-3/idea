package pl.dmcs.idea.exceptions;

public class AppBaseException extends Exception {

    public static final String UNEXPECTED_ERROR = "unexpected.error";

    public AppBaseException() {
        super();
    }

    public AppBaseException(String message) {
        super(message);
    }
}
