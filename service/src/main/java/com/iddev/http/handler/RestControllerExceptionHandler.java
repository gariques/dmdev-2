package com.iddev.http.handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "com/iddev/http/rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
