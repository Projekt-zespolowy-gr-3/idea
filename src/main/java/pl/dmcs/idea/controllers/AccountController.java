package pl.dmcs.idea.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.idea.dto.AccountDTO;
import pl.dmcs.idea.exceptions.AppBaseException;
import pl.dmcs.idea.services.AccountService;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register/{captcha}")
    public ResponseEntity<?> register(@Valid @RequestBody AccountDTO accountDTO) {
        try {
            accountDTO.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
            String token = accountService.register(accountDTO);
            return ResponseEntity.ok("registration.success");
        } catch (AppBaseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("unexpected.error");
        }
    }

    @PostMapping("/confirm/{token}")
    public ResponseEntity<?> confirm(@PathVariable String token) {
        try {
            accountService.confirm(token);
            return ResponseEntity.ok("confirmation.success");
        } catch (AppBaseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("unexpected.error");
        }
    }

    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAccounts() {
        try {
            return ResponseEntity.ok().body(accountService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("unexpected.error");
        }
    }

    @PostMapping("/changeRole/{login}/{role}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> changeRole(@PathVariable String login, @PathVariable String role) {
        try {
            accountService.changeRole(login, role);
            return ResponseEntity.ok().body("success");
        } catch (AppBaseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("unexpected.error");
        }
    }

    @GetMapping("/user/{login}")
    @PreAuthorize("hasAuthority('CLIENT') and #login == authentication.principal.username")
    public ResponseEntity<?> getUser(@PathVariable String login) {
        try {
            return ResponseEntity.ok().body(accountService.getUser(login));
        } catch (AppBaseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("unexpected.error");
        }
    }
}
