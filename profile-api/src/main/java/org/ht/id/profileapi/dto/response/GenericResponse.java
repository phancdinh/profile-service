package org.ht.id.profileapi.dto.response;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class GenericResponse extends HashMap<String, Object> {

    public void setSuccess(HttpStatus status, Object data) {
        this.put("status", status.value());
        this.put("data", data);
    }

    public void setFailure(HttpStatus status, String message, Object details) {
        setFailure(status, message);
        this.put("details", details);
    }

    public void setFailure(HttpStatus status, String message) {
        this.put("status", status.value());
        this.put("message", message);
    }
}
