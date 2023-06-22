package pw.paint.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pw.paint.DTOs.requests.AuthenticationRequest;
import pw.paint.DTOs.model.AuthenticationResponse;
import pw.paint.DTOs.requests.RegisterRequest;
import pw.paint.exception.DataConflictException;
import pw.paint.model.Folder;
import pw.paint.model.User;
import pw.paint.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = userRepository.findByUsername(request.getUsername());
        if(user.isEmpty()){
            List<Folder> baseFolders = new ArrayList<>();
            baseFolders.add(new Folder("moje autorskie przepisy"));
            baseFolders.add(new Folder("moje ulubione przepisy"));

            var newUser = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .folders(baseFolders)
                    .build();

            userRepository.save(newUser);

            var jwtToken = jwtService.generateToken(newUser);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        else
            throw new DataConflictException("This username is already taken");
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
