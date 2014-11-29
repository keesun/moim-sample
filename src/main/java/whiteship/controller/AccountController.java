package whiteship.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import whiteship.controller.support.AccountDTO;
import whiteship.domain.Account;
import whiteship.repository.AccountRepository;
import whiteship.service.AccountService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Keeun Baik
 */
@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity createNewAccount(@RequestBody @Valid AccountDTO.RequestToCreate accountDTO,
                                           BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Account newAccount = accountService.createNew(accountDTO);
        AccountDTO.Response responseDTO = modelMapper.map(newAccount, AccountDTO.Response.class);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public ResponseEntity getAccounts(Pageable pageable) {
        Page<Account> accountPage = accountRepository.findAll(pageable);
        List<AccountDTO.Response> results = accountPage.getContent().stream()
                .map(account -> modelMapper.map(account, AccountDTO.Response.class))
                .collect(Collectors.toList());
        PageImpl<AccountDTO.Response> response = new PageImpl<>(results, pageable, accountPage.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/account/{username}", method = RequestMethod.GET)
    public ResponseEntity getAccount(@PathVariable String username) {
        Account account = accountRepository.findByUsername(username);

        if (account == null) {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }

        AccountDTO.Response responseDTO = modelMapper.map(account, AccountDTO.Response.class);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
