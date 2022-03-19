package com.main.simpleloginwithjwt.dto;

public class DefaultResponse {

    private boolean success;
    private String messages;
    private Object result;
    private Object additionalEntity;

    public DefaultResponse() {
    }

    public DefaultResponse(boolean success, String messages) {
        this.success = success;
        this.messages = messages;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getAdditionalEntity() {
        return additionalEntity;
    }

    public void setAdditionalEntity(Object additionalEntity) {
        this.additionalEntity = additionalEntity;
    }
}
