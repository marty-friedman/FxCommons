package com.egrasoft.fxcommons.throwable;

public class DataProcessingException extends RuntimeException {
    private String[] messageParameters;

    public DataProcessingException(String ... messageParameters) {
        this.messageParameters = messageParameters;
    }

    public String[] getMessageParameters() {
        return messageParameters;
    }

    public void setMessageParameters(String[] messageParameters) {
        this.messageParameters = messageParameters;
    }
}
