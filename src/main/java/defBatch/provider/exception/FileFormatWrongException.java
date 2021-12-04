package defBatch.provider.exception;

public class FileFormatWrongException extends Exception {
    private static final long serialVersionUID = -7069990442036753391L;

    private String message;

    public FileFormatWrongException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
