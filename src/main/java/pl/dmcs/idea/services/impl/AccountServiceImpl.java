package pl.dmcs.idea.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.dmcs.idea.dto.AccountDTO;
import pl.dmcs.idea.dto.mappers.AccountMapper;
import pl.dmcs.idea.entities.*;
import pl.dmcs.idea.exceptions.AppBaseException;
import pl.dmcs.idea.repositories.AccountRepository;
import pl.dmcs.idea.services.AccountService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = AppBaseException.class)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<AccountDTO> findAll() {
        return accountRepository.findAll().stream().map(AccountMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public String register(AccountDTO accountDTO) throws AppBaseException {
        try {
            Account account = AccountMapper.mapFromDto(accountDTO);
            account.setAccessLevels(generateAccessLevels(account));
            account.setActive(false);
            account.setToken(UUID.randomUUID().toString());
            accountRepository.saveAndFlush(account);
            return account.getToken();
        } catch (DataIntegrityViolationException e) {
            throw new AppBaseException("login.or.email.exists.error");
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }

    private List<AccessLevel> generateAccessLevels(Account account) {
        Client client = new Client();
        client.setAccount(account);
        client.setActive(true);
        client.setAccessLevel("CLIENT");

        Admin admin = new Admin();
        admin.setAccount(account);
        admin.setActive(false);
        admin.setAccessLevel("ADMIN");

        return Arrays.asList(client, admin);
    }

    @Override
    public void confirm(String token) throws AppBaseException {
        try {
            Account account = accountRepository.findByToken(token)
                    .orElseThrow(() -> new AppBaseException("unexpected.error"));
            if(account.isActive()) throw new AppBaseException("account.confirmed.error");
            account.setActive(true);
            accountRepository.saveAndFlush(account);
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }

    @Override
    public void changeRole(String login, String role) throws AppBaseException {
        try {
            Account account = accountRepository.findByLogin(login)
                    .orElseThrow(() -> new AppBaseException("unexpected.error"));
            AccessLevel accessLevel = account.getAccessLevels().stream().filter(a -> a.getAccessLevel().equals(role)).findFirst()
                    .orElseThrow(() -> new AppBaseException("unexpected.error"));
            accessLevel.setActive(!accessLevel.isActive());
            Optional<AccessLevel> accessLevelOptional = account.getAccessLevels().stream().filter(AccessLevel::isActive).findAny();
            if(accessLevelOptional.isEmpty()) throw new AppBaseException("empty.roles.error");
            accountRepository.saveAndFlush(account);
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }

    @Override
    public AccountDTO getUser(String login) throws AppBaseException {
        try {
            return AccountMapper.mapToDto(accountRepository.findByLogin(login)
                    .orElseThrow(() -> new AppBaseException("unexpected.error")));
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }

    @Override
    public AccountDTO getUserByEmail(String email) throws AppBaseException {
        try {
            return AccountMapper.mapToDto(accountRepository.findByEmail(email)
                    .orElseThrow(() -> new AppBaseException("unexpected.error")));
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }

    @Override
    public void changeResetPassword(String token, AccountDTO accountDTO) throws AppBaseException {
        try {
            Account account = accountRepository.findByToken(token)
                    .orElseThrow(() -> new AppBaseException("unexpected.error"));
            if(!account.getToken().equals(token))
                throw new AppBaseException("unexpected.error");
            account.setPassword(accountDTO.getPassword());
            accountRepository.saveAndFlush(account);
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }
}
