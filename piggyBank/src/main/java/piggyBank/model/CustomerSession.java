package piggyBank.model;

public class CustomerSession {

    private final Customer customer;
    private boolean loggedIn;

    public CustomerSession(Customer customer) {
        this.customer = customer;
        this.loggedIn = true;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
