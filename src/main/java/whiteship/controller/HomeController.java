package whiteship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Keeun Baik
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public @ResponseBody String home() {
        return "Hello Spring Boot";
    }

}
