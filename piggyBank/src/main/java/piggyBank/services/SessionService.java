package piggyBank.services;

import piggyBank.model.CustomerSession;

import javax.servlet.http.HttpSession;

public interface SessionService {

    CustomerSession authenticate(String username, String password, HttpSession httpSession);

    CustomerSession getCurrentSession(HttpSession httpSession);
    boolean endCurrentSession(HttpSession httpSession);

}
