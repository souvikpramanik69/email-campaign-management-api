package com.kasplo.email_campaign_management_api.common.controller;

import com.kasplo.email_campaign_management_api.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RootController {

    @GetMapping
    public ResponseEntity<ApiResponse<Void>> root() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK.value(), "Welcome to Email Campaign Management Application"));
    }



}