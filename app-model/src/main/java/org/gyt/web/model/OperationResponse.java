package org.gyt.web.model;

/**
 * 操作相应对象
 * Created by y27chen on 2016/11/21.
 */
public class OperationResponse {

    private long timestamp;

    private ResponseType status = ResponseType.OK;

    private String error;

    private String message;

    private String path;

    private Exception exception;

    public OperationResponse() {
        timestamp = System.currentTimeMillis();
    }

    public OperationResponse(ResponseType status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public OperationResponse(ResponseType status, String message, String path) {
        this();
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public OperationResponse(String error, String path, Exception exception) {
        this();
        this.error = error;
        this.path = path;
        this.exception = exception;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ResponseType getStatus() {
        return status;
    }

    public void setStatus(ResponseType status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public enum ResponseType {
        OK,
        ERROR
    }
}
