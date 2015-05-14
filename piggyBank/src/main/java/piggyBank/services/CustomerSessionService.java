package piggyBank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import piggyBank.model.Customer;
import piggyBank.model.CustomerRepository;
import piggyBank.model.CustomerSession;

import javax.servlet.http.HttpSession;

@Component
public class CustomerSessionService implements SessionService {

    public  static final String USER_SESSION_ATTRIBUTE = "piggyBank.UserSession";
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerSessionService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerSession authenticate(String username, String password, HttpSession httpSession) {
        Customer customer = customerRepository.lookupUsername(username);
        if (customer == null) {
            return null;
        }

        if (!customer.isPassword(password)) {
            return null;
        }

        CustomerSession customerSession = new CustomerSession(customer);
        httpSession.setAttribute(USER_SESSION_ATTRIBUTE, customerSession);
        return customerSession;
    }

    @Override
    public CustomerSession getCurrentSession(HttpSession httpSession) {
        return (CustomerSession) httpSession.getAttribute(USER_SESSION_ATTRIBUTE);
    }

    @Override
    public boolean endCurrentSession(HttpSession httpSession) {
        CustomerSession currentSession = getCurrentSession(httpSession);
        httpSession.invalidate();

        if (currentSession != null) {
            currentSession.setLoggedIn(false);
            return true;
        }
        return false;
    }

}
