package whiteship.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import whiteship.Application;
import whiteship.controller.support.AccountDTO;
import whiteship.domain.Account;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@Transactional
public class AccountServiceTest {

    @Autowired AccountService accountService;

    @Autowired PasswordEncoder passwordEncoder;

    @Test
    public void passwordTest() {
        // given
        AccountDTO.RequestToCreate dto = new AccountDTO.RequestToCreate();
        dto.setUsername("whiteship");
        dto.setPassword("password");

        // When
        Account newAccount = accountService.createNew(dto);

        // Then
        assertThat(passwordEncoder.matches("password", newAccount.getPassword()), is(true));
    }

}