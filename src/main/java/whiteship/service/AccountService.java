package whiteship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import whiteship.controller.support.AccountDTO;
import whiteship.domain.Account;
import whiteship.repository.AccountRepository;

import javax.transaction.Transactional;

/**
 * @author Keeun Baik
 */
@Transactional
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account createNew(AccountDTO.RequestToCreate accountDTO) {
        Account account = new Account();
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        account.setUsername(accountDTO.getUsername());
        return accountRepository.save(account);
    }
}
