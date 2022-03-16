package pl.dmcs.idea.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.dmcs.idea.security.JwtRequest;
import pl.dmcs.idea.security.JwtResponse;
import pl.dmcs.idea.security.JwtTokenUtils;

@Slf4j
@CrossOrigin
@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    jwtRequest.getLogin(), jwtRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            log.error("Handled exception", e);
            return ResponseEntity.badRequest().body("credentials.error");
        } catch (LockedException | DisabledException e) {
            log.error("Handled exception", e);
            return ResponseEntity.badRequest().body("account.disabled.error");
        } catch (AuthenticationException e) {
            log.error("Handled exception", e);
            return ResponseEntity.badRequest().body("unexpected.error");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
