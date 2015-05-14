package piggyBank.services;

import piggyBank.model.Customer;

public interface TransferService {

    void transfer(Customer fromCustomer, String toUsername, String amount) throws InvalidAmountException, InvalidBalanceException, InvalidDestinationUsernameException;

}
