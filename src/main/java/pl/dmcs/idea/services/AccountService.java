package pl.dmcs.idea.services;



import pl.dmcs.idea.dto.AccountDTO;
import pl.dmcs.idea.exceptions.AppBaseException;

import java.util.List;

public interface AccountService {

    List<AccountDTO> findAll();
    String register(AccountDTO accountDTO) throws AppBaseException;
    void confirm(String token) throws AppBaseException;
    void changeRole(String login, String role) throws AppBaseException;
    AccountDTO getUser(String login) throws AppBaseException;
}
