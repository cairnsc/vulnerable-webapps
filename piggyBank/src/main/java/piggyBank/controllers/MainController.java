package piggyBank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import piggyBank.model.CustomerSession;
import piggyBank.services.SessionService;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    private final SessionService sessionService;

    @Autowired
    public MainController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String base(HttpSession httpSession) {
        CustomerSession customerSession = sessionService.getCurrentSession(httpSession);
        if (customerSession != null) {
            return "redirect:" + BalanceController.TEMPLATE_NAME;
        } else {
            return "redirect:" + LoginController.TEMPLATE_NAME;
        }
    }

}
