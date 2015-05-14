package piggyBank.services;

public class InvalidDestinationUsernameException extends Exception {

    public InvalidDestinationUsernameException(String username) {
        super("Unknown username " + username);
    }

}
