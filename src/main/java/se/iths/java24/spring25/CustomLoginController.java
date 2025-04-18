package se.iths.java24.spring25;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomLoginController {
    @GetMapping("/custom-login")
    public String customLogin() {
        return "custom_login";
    }
}
