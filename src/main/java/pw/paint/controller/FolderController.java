package pw.paint.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.paint.DTOs.model.ShortRecipeDto;
import pw.paint.exception.FolderNotFoundException;
import pw.paint.exception.RecipeAlreadyInFolder;
import pw.paint.exception.UserNotFoundException;
import pw.paint.service.FolderService;
import pw.paint.service.JwtService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/folder")
public class FolderController {
    private final FolderService folderService;
    private final JwtService jwtService;

    @GetMapping("/{username}")
    public ResponseEntity<List<String>> getUserFolders(@PathVariable String username,
            @CookieValue(name = "token", required = false) String token) {
        try {
            if (token == null || !jwtService.extractUsername(token).equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .header("Error-message", "Forbidden")
                        .build();
            }
            return ResponseEntity.ok(folderService.getFoldersNames(username));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }

    @GetMapping("/{username}/{folderName}")
    public ResponseEntity<List<ShortRecipeDto>> getRecipesFromFolder(
            @PathVariable String username,
            @PathVariable String folderName,
            @CookieValue(name = "token", required = false) String token
    ) {
        try {
            if (token == null || !jwtService.extractUsername(token).equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .header("Error-message", "Forbidden")
                        .build();
            }
            return ResponseEntity.ok(folderService.getRecipesFromFolder(username, folderName));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }

    @PostMapping("/new/{username}/{folderName}")
    public ResponseEntity<Void> createNewFolder(
            @PathVariable String username,
            @PathVariable String folderName,
            @CookieValue(name = "token", required = false) String token
    ) {
        try {
            if (token == null || !jwtService.extractUsername(token).equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .header("Error-message", "Forbidden")
                        .build();
            }
            folderService.createNewFolder(username, folderName);
            return ResponseEntity.created(new URI("/folder/" + username)).build();
        } catch (UserNotFoundException | FolderNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Error-message", ex.getMessage())
                    .build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/add/{username}/{folderName}/{recipeId}")
    public ResponseEntity<Void> addRecipeToFolder(
            @PathVariable String username,
            @PathVariable String folderName,
            @PathVariable String recipeId,
            @CookieValue(name = "token", required = false) String token
    ) {
        try {
            if (token == null || !jwtService.extractUsername(token).equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .header("Error-message", "Forbidden")
                        .build();
            }
            return ResponseEntity.created(new URI("/recipe/" +
                    folderService.addRecipeToFolder(username, folderName, recipeId))).build();
        } catch (RecipeAlreadyInFolder e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Error-Message", e.getMessage())
                    .build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{username}/{folderName}")
    public ResponseEntity<String> deleteFolder(
            @PathVariable String username,
            @PathVariable String folderName,
            @CookieValue(name = "token", required = false) String token
    ) {
        try {
            if (token == null || !jwtService.extractUsername(token).equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .header("Error-message", "Forbidden")
                        .build();
            }
            return ResponseEntity.ok(folderService.deleteFolder(username, folderName));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{username}/{folderName}/{recipeId}")
    public ResponseEntity<String> deleteRecipeFromFolder(
            @PathVariable String username,
            @PathVariable String folderName,
            @PathVariable String recipeId,
            @CookieValue(name = "token", required = false) String token
    ) {
        try {
            if (token == null || !jwtService.extractUsername(token).equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .header("Error-message", "Forbidden")
                        .build();
            }
            return ResponseEntity.ok(folderService.deleteRecipeFromFolder(username, folderName, recipeId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }
}
