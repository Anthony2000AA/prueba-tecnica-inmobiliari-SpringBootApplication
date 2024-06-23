package com.optimainmobiliaria.shared.exception;

import com.optimainmobiliaria.shared.exception.dto.ErrorMessageResponse;
import com.optimainmobiliaria.shared.model.dto.api_response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ApiResponse<ErrorMessageResponse> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest
    ){
        var errorMessageResponse= new ErrorMessageResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        log.error(exception.getMessage());
        return new ApiResponse<>(false, exception.getMessage(), errorMessageResponse);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(value= HttpStatus.EXPECTATION_FAILED)
    public ApiResponse<Map<String, Object>> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "File too large!");
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", LocalDateTime.now());
        data.put("message", "Maximum upload size exceeded");
        data.put("details", exc.getMessage());
        response.put("data", data);

        return new ApiResponse<>(false, "Maximum upload size exceeded", response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    protected ApiResponse<?> handleValidationErrors(
            MethodArgumentNotValidException exception,
            WebRequest webRequest
    ){
        List<String> errors= exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error-> error.getField()+": "+error.getDefaultMessage())
                .collect(Collectors.toList());

        return new ApiResponse<>(false, "Validation errors", errors);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<ErrorMessageResponse> handleGlobalException(
            Exception exception,
            WebRequest webRequest
    ){
        var errorMessage = new ErrorMessageResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );

        return new ApiResponse<>(false,"Internal server error", errorMessage);
    }

    @ExceptionHandler(FileDownloadException.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<ErrorMessageResponse> handleFileDownloadException(
            FileDownloadException exception,
            WebRequest webRequest
    ){
        var errorMessage = new ErrorMessageResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );

        return new ApiResponse<>(false,"Error al descargar el archivo", errorMessage);
    }


}
