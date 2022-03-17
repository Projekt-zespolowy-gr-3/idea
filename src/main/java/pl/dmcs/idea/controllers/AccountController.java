package pl.dmcs.idea.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.idea.dto.AccountDTO;
import pl.dmcs.idea.exceptions.AppBaseException;
import pl.dmcs.idea.services.AccountService;
import pl.dmcs.idea.utils.EmailService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AccountDTO accountDTO, HttpServletRequest request) throws AppBaseException, MessagingException {
        accountDTO.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        String token = accountService.register(accountDTO);
        emailService.sendRegistrationMail(accountDTO.getEmail(), token, request.getRequestURL().toString(), request.getServletPath());
        return ResponseEntity.ok("registration.success");
    }

    @PostMapping("/confirm/{token}")
    public ResponseEntity<String> confirm(@PathVariable String token) throws AppBaseException {
        accountService.confirm(token);
        return ResponseEntity.ok("confirmation.success");
    }

    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AccountDTO> getAccounts() {
        return accountService.findAll();
    }

    @PostMapping("/changeRole/{login}/{role}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeRole(@PathVariable String login, @PathVariable String role) throws AppBaseException {
        accountService.changeRole(login, role);
        return "success";
    }

    @GetMapping("/user/{login}")
    @PreAuthorize("hasAuthority('CLIENT') and #login == authentication.principal.username")
    public AccountDTO getUser(@PathVariable String login) throws AppBaseException {
        return accountService.getUser(login);
    }
}
