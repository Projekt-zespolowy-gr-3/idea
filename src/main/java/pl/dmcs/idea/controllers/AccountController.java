package pl.dmcs.idea.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.idea.dto.AccountDTO;
import pl.dmcs.idea.exceptions.AppBaseException;
import pl.dmcs.idea.services.AccountService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public void register(@Valid @RequestBody AccountDTO accountDTO) throws AppBaseException {
        accountDTO.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        String token = accountService.register(accountDTO);
    }

    @PostMapping("/confirm/{token}")
    public String confirm(@PathVariable String token) throws AppBaseException {
        accountService.confirm(token);
        return "confirmation.success";
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
