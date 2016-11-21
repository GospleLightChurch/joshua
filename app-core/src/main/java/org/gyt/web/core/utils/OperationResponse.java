package org.gyt.web.core.utils;

/**
 * 操作相应对象
 * Created by y27chen on 2016/11/21.
 */
public class OperationResponse {

    private long timestamp;

    private ResponseType status = ResponseType.OK;

    private String message;

    private String path;

    public OperationResponse() {
        timestamp = System.currentTimeMillis();
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

    public enum ResponseType {
        OK,
        ERROR
    }
}
