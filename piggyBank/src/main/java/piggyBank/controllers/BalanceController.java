package piggyBank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import piggyBank.model.CustomerSession;
import piggyBank.services.SessionService;

import javax.servlet.http.HttpSession;

@Controller
public class BalanceController {

    public static final String TEMPLATE_NAME = "balance";
    private final SessionService sessionService;

    @Autowired
    public BalanceController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public String logout(Model model, HttpSession httpSession) {
        CustomerSession customerSession = sessionService.getCurrentSession(httpSession);
        if (customerSession == null ) {
            return "redirect:" + LoginController.TEMPLATE_NAME;
        }

        model.addAttribute("customer", customerSession.getCustomer());

        return TEMPLATE_NAME;
    }

}
