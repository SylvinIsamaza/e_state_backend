package com.example.estate.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Response<T> {
    public boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
 public T data;
 @JsonInclude(JsonInclude.Include.NON_NULL)
 public String message;
 
public Response(boolean success, T data) {
    this.success = success;
    this.data = data;
}
public Response(boolean success, T data,String message) {
    this.success = success;
    this.data = data;
    this.message = message;
}
@Override
public String toString() {
    if (message == null) {
        return "{success=" + success + ", data=" + data + "}";
    }
    else {
        return "{success=" + success +","+ data + "}";  
    }
}
 
}
