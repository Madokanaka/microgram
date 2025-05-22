package org.attractor.microgram.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.attractor.microgram.exception.BadRequestException;
import org.attractor.microgram.exception.NoAccessException;
import org.attractor.microgram.exception.RecordAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFoundException(Model model, HttpServletRequest request, NoSuchElementException e) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("message", e.getMessage());
        return "errors/error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String validationHandler(Model model, HttpServletRequest request, MethodArgumentNotValidException e) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("message", e.getMessage());
        return "errors/error";
    }

    @ExceptionHandler(RecordAlreadyExistsException.class)
    public String handleRecordAlreadyExistsException(Model model, HttpServletRequest request, RecordAlreadyExistsException e) {
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("reason", HttpStatus.CONFLICT.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("message", e.getMessage());
        return "errors/error";    }

    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequestException(Model model, HttpServletRequest request, BadRequestException e) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("message", e.getMessage());
        return "errors/error";    }

    @ExceptionHandler(NoAccessException.class)
    public String handleNoAccessException(Model model, HttpServletRequest request, NoAccessException e) {
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reason", HttpStatus.FORBIDDEN.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("message", e.getMessage());
        return "errors/error";    }

}
