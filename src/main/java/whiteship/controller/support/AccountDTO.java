package whiteship.controller.support;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Keeun Baik
 */
public class AccountDTO {

    @Data
    public static class RequestToCreate {

        @NotEmpty
        private String username;

        @NotEmpty
        private String password;

    }

    @Data
    public static class Response {

        private Long id;

        private String username;

        private String email;

    }

}
