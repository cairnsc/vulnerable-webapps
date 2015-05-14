package piggyBank.services;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import piggyBank.model.Customer;
import piggyBank.model.CustomerRepository;

import java.math.BigDecimal;

@Component
public class TransferServiceImpl implements TransferService {

    private CustomerRepository customerRepository;

    @Autowired
    public TransferServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void transfer(Customer fromCustomer, String toUsername, String amount) throws InvalidAmountException, InvalidBalanceException, InvalidDestinationUsernameException {
        BigDecimal decimalAmount = new BigDecimal(amount);
        if (decimalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(amount);
        }

        Customer toCustomer = customerRepository.lookupUsername(toUsername);
        if (toCustomer == null) {
            throw new InvalidDestinationUsernameException(toUsername);
        }

        Money transferAmount = Money.of(CurrencyUnit.USD, decimalAmount);
        fromCustomer.withdraw(transferAmount);
        toCustomer.deposit(transferAmount);
    }

}
