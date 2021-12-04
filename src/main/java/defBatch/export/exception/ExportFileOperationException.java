package defBatch.export.exception;

public class ExportFileOperationException extends Exception {
    private static final long serialVersionUID = 1L;

    public enum OperationType {
        OPEN, WRITE, CLOSE
    }
    
    private OperationType operationType;
    
    public ExportFileOperationException(OperationType operationType, String message) {
        super(buildMessage(operationType, message));
        this.operationType = operationType;
    }

    public ExportFileOperationException(OperationType operationType, String message, Throwable cause) {
        super(buildMessage(operationType, message), cause);
    }
    
    private static String buildMessage(OperationType operationType, String message) {
        return String.format("Operation type: [%s], Error: [%s]", operationType.name(), message);
    }

    public OperationType getOperationType() {
        return operationType;
    }
}
