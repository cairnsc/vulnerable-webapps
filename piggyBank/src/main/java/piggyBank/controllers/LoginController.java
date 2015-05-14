package piggyBank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import piggyBank.model.CustomerSession;
import piggyBank.services.SessionService;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    public final static String TEMPLATE_NAME = "login";
    private final SessionService sessionService;

    @Autowired
    public LoginController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return TEMPLATE_NAME;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password, Model model, final HttpSession httpSession) {
        CustomerSession customerSession = sessionService.authenticate(username, password, httpSession);
        if (customerSession == null) {
            model.addAttribute("authError", true);
            return TEMPLATE_NAME;
        }
        return "redirect:" + BalanceController.TEMPLATE_NAME;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpSession httpSession) {
        boolean result = sessionService.endCurrentSession(httpSession);
        if (result != false) {
            model.addAttribute("loggedOut", true);
        }
        return TEMPLATE_NAME;
    }

}
