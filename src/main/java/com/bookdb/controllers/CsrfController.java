package com.bookdb.controllers;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class CsrfController {
    @GetMapping("/csrf-token")
    public Map<String, String> getCsrfToken(CsrfToken token) {
        return Map.of(
                token.getHeaderName(), token.getToken()
        );
    }

}
