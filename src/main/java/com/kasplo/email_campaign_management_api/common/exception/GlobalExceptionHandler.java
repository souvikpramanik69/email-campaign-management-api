package com.kasplo.email_campaign_management_api.common.exception;

import com.kasplo.email_campaign_management_api.common.response.ApiResponse;
import com.kasplo.email_campaign_management_api.common.response.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationError(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        Map<String,Object> response = new HashMap<>();
        response.put("code",404);
        response.put("success",false);
        response.put("message","Validation failed");
        response.put("errors",errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    @ExceptionHandler(InvalidScheduledAtException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidScheduleAtException(
            InvalidScheduledAtException ex
    ) {
     return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body( ApiResponse.error(400,ex.getMessage()));
    }

    @ExceptionHandler(NoRecipientsInCampaignException.class)
    public ResponseEntity<ApiResponse<String>> handleNoRecipientsInCampaignException(
            NoRecipientsInCampaignException ex
    ) {
     return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body( ApiResponse.error(404,ex.getMessage()));
    }

    @ExceptionHandler(InvalidCampaignScheduleException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidCampaignScheduleException(
            InvalidCampaignScheduleException ex
    ) {
     return ResponseEntity.status(HttpStatus.CONFLICT.value()).body( ApiResponse.error(409,ex.getMessage()));
    }

    @ExceptionHandler(RecipientAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleRecipientAlreadyExistException(
            RecipientAlreadyExistsException ex
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body( ApiResponse.error(409,ex.getMessage()));
    }

    @ExceptionHandler(InvalidCampaignStateException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidCampaignStateException(
            InvalidCampaignStateException ex
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body( ApiResponse.error(409,ex.getMessage()));
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(
            NoResourceFoundException ex) {
        String path = ex.getResourcePath();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.success(404, "Resource not found: " + path));
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiResponse.success(405, "Http method '" + ex.getMethod() + "' is not supported"));
    }


}





