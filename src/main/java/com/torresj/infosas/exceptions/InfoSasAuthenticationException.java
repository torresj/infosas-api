package com.torresj.infosas.exceptions;

public class InfoSasAuthenticationException extends RuntimeException {
    public InfoSasAuthenticationException() {
        super("Authentication failed");
    }
}
