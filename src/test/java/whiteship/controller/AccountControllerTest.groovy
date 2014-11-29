package whiteship.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification
import whiteship.Application
import whiteship.controller.support.AccountDTO
import whiteship.repository.AccountRepository
import whiteship.security.MoinUserDetails
import whiteship.service.AccountService

import javax.servlet.Filter

import static org.hamcrest.Matchers.is
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author Keeun Baik
 */
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = Application.class)
@WebAppConfiguration
@IntegrationTest
class AccountControllerTest extends Specification {

    @Autowired
    private WebApplicationContext wac

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private AccountService accountService

    @Autowired
    private AccountRepository accountRepository

    @Autowired
    private Filter springSecurityFilterChain

    def mockMvc

    def accountDTO

    def setup() {
        accountRepository.deleteAll();
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(springSecurityFilterChain)
                .build()
        accountDTO = new AccountDTO.RequestToCreate(username: "whiteship", password: "password")
    }

    def "Account 생성하기"() {
        when: "정상적인 데이터 넘기면"
        def successfulResult = mockMvc.perform(post("/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(accountDTO)))

        then: "새로 생성된 계정 정보 응답"
        successfulResult.andDo(print())
        successfulResult.andExpect(status().isCreated())
        successfulResult.andExpect(jsonPath("\$.username", is("whiteship")))

        when: "이상한 데이터 넘기면"
        accountDTO.username = ""
        def errorResult = mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDTO)))

        then: "에러 메시지 응답"
        errorResult.andDo(print())
        errorResult.andExpect(status().isBadRequest())
    }

    def "Account 정보 조회하기"() {
        given:
        def account = accountService.createNew(accountDTO)

        when:
        def result = mockMvc.perform(get("/account/whiteship")
                            .with(user(new MoinUserDetails(account)))
                            .contentType(MediaType.APPLICATION_JSON))

        then:
        result.andDo(print())
        result.andExpect(status().isOk())
    }

}
