package piggyBank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import piggyBank.model.CustomerSession;
import piggyBank.services.SessionService;
import piggyBank.services.TransferService;

import javax.servlet.http.HttpSession;

@Controller
public class TransferController {

    public static final String TEMPLATE_NAME = "transfer";
    public static final String TEMPLATE_NAME_SUCCESS = "transferSuccess";
    private final SessionService sessionService;
    private TransferService transferService;

    @Autowired
    public TransferController(SessionService sessionService, TransferService transferService) {
        this.sessionService = sessionService;
        this.transferService = transferService;
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public String transfer(HttpSession httpSession) {
        CustomerSession customerSession = sessionService.getCurrentSession(httpSession);
        if (customerSession == null ) {
            return "redirect:" + LoginController.TEMPLATE_NAME;
        }
        return TEMPLATE_NAME;
    }

    @RequestMapping(value = "/transferExecute", method = RequestMethod.GET)
    public String transferExecute(@RequestParam String destination, @RequestParam String amount, Model model, HttpSession httpSession) {
        CustomerSession customerSession = sessionService.getCurrentSession(httpSession);
        if (customerSession == null ) {
            return "redirect:" + LoginController.TEMPLATE_NAME;
        }

        try {
            transferService.transfer(customerSession.getCustomer(), destination, amount);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return TEMPLATE_NAME;
        }
        return TEMPLATE_NAME_SUCCESS;
    }

}
