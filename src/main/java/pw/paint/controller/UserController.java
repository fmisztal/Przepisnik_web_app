package pw.paint.controller;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.paint.DTOs.model.*;
import pw.paint.exception.UserNotFoundException;
import pw.paint.service.JwtService;
import pw.paint.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id,
                                           @CookieValue(name = "token", required = false) String token ) {
        try {
            var user = userService.getUserById(new ObjectId(id));
            if(token == null || !user.getUserName().equals(jwtService.extractUsername(token))){
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Error-Message", "Access forbidden")
                        .build();
            }
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }
}
