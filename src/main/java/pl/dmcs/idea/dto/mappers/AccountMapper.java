package pl.dmcs.idea.dto.mappers;


import pl.dmcs.idea.dto.AccountDTO;
import pl.dmcs.idea.entities.AccessLevel;
import pl.dmcs.idea.entities.Account;

import java.util.stream.Collectors;

public class AccountMapper {

    public static AccountDTO mapToDto(Account account) {
        return AccountDTO.builder()
                .login(account.getLogin())
                .name(account.getFirstname())
                .surname(account.getLastname())
                .email(account.getEmail())
                .accessLevels(account.getAccessLevels().stream().filter(AccessLevel::isActive).map(AccessLevel::getAccessLevel).collect(Collectors.toList()))
                .build();
    }

    public static Account mapFromDto(AccountDTO accountDTO) {
        return Account.builder()
                .login(accountDTO.getLogin())
                .password(accountDTO.getPassword())
                .active(accountDTO.isActive())
                .firstname(accountDTO.getName())
                .lastname(accountDTO.getSurname())
                .email(accountDTO.getEmail())
                .build();
    }
}
