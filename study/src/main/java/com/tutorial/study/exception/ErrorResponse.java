package com.tutorial.study.exception;

import java.util.Map;

public class ErrorResponse {
    private int status;
    private String messages;
    private Map<String, String> error;

    public ErrorResponse(int status, String messages, Map<String, String> error) {
        this.status = status;
        this.messages = messages;
        this.error = error;
    }
    public int getStatus() {
        return status;
    }
    public String getMessages(){
        return messages;
    }

    public Map<String, String> getError() {
        return error;
    }

    public void setError(Map<String, String> error) {
        this.error = error;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public void setMessages(String messages){
        this.messages = messages;
    }
}
