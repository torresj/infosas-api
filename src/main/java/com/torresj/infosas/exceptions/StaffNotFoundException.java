package com.torresj.infosas.exceptions;

public class StaffNotFoundException extends RuntimeException {
    public StaffNotFoundException(Long id) {
        super("Could not find staff with id " + id);
    }
}
