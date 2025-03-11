package com.torresj.infosas.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("Dni or Surname are mandatory (only use one of them)");
    }
}
