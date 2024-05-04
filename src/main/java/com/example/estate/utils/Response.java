package com.example.estate.utils;

public class Response<T> {
 public boolean success;
 public T data;
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
    return "{success=" + success + ", data=" + data + "}";
}
 
}
