package org.attractor.microgram.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFoundException(Model model, HttpServletRequest request, NoSuchElementException e) {
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

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("error", "Файл слишком большой. Максимальный размер файла: 10MB.");
        String referer = request.getHeader("Referer");

        return "redirect:" + (referer != null ? referer : "/profile");
    }

    @ExceptionHandler(MultipartException.class)
    public String handleMultipartException(MultipartException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("error", "Ошибка при загрузке файла: файл слишком большой или повреждён.");

        String referer = request.getHeader("Referer");

        return "redirect:" + (referer != null ? referer : "/profile");
    }
}
