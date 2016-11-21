package org.gyt.web.core.utils;

public class OperationResponseFactory {
    private OperationResponseFactory() {
    }

    public static OperationResponse ok(String message) {
        OperationResponse operationResponse = new OperationResponse();
        operationResponse.setStatus(OperationResponse.ResponseType.OK);
        operationResponse.setMessage(message);
        return operationResponse;
    }

    public static OperationResponse error(String message) {
        OperationResponse operationResponse = new OperationResponse();
        operationResponse.setStatus(OperationResponse.ResponseType.ERROR);
        operationResponse.setMessage(message);
        return operationResponse;
    }
}
