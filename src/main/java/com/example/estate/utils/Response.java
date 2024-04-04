package com.example.estate.utils;

public class Response<T> {
 public boolean success;
 public T data;
 
public Response(boolean success, T data) {
    this.success = success;
    this.data = data;
}

@Override
public String toString() {
    return "{success=" + success + ", data=" + data + "}";
}
 
}
