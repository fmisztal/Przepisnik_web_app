package pw.paint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.paint.DTOs.requests.AuthenticationRequest;
import pw.paint.DTOs.model.AuthenticationResponse;
import pw.paint.DTOs.requests.RegisterRequest;
import pw.paint.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(service.register(request));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        var token = service.authenticate(request);
        HttpHeaders headers = new HttpHeaders();

        ResponseCookie springCookie = ResponseCookie.from("token", token.getToken())
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(36000)
                .build();
        headers.add(HttpHeaders.SET_COOKIE, springCookie.toString());
        return new ResponseEntity<>(
                token, headers, HttpStatus.OK);
    }
}
