package org.onlineshop.in.rest.common;

public record ErrorEntity(int httpStatus, String errorMessage) {}
