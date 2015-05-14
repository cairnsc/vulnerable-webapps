package piggyBank.model;

import org.joda.money.Money;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomerRepository {

    private Map<String, Customer> customerMap;

    public CustomerRepository() {
        customerMap = new HashMap<>();
        addCustomer(new Customer("test", "test", "Firstname Lastname", Money.parse("USD 500")));
        addCustomer(new Customer("evil", "user", "Evil User", Money.parse("USD 0")));
    }

    public void addCustomer(Customer customer) {
        customerMap.put(customer.getUsername(), customer);
    }

    public Customer lookupUsername(String username) {
        return customerMap.get(username);
    }

}
