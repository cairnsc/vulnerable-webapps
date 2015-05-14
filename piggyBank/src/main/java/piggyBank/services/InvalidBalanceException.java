package piggyBank.services;

public class InvalidBalanceException extends Exception {

    public InvalidBalanceException(String message) {
        super(message);
    }

}
