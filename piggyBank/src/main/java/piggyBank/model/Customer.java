package piggyBank.model;

import org.joda.money.Money;
import org.joda.money.format.MoneyAmountStyle;
import org.joda.money.format.MoneyFormatter;
import org.joda.money.format.MoneyFormatterBuilder;
import piggyBank.services.InvalidBalanceException;

public class Customer {

    private String username;
    private String password;
    private String fullName;
    private Money balance;

    public Customer(String username, String password, String fullName, Money balance) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.balance = balance;
    }

    public boolean isPassword(String password) {
        return this.password.equals(password);
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public Money getBalance() {
        return balance;
    }

    public String getBalanceString() {
        MoneyFormatter formatter = new MoneyFormatterBuilder()
                .appendLiteral("$")
                .appendAmount(MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA)
                .toFormatter();
        return formatter.print(balance);
    }

    public void withdraw(Money amount) throws InvalidBalanceException {
        Money newBalance = balance.minus(amount);
        if (newBalance.isNegativeOrZero()) {
            throw new InvalidBalanceException("Withdrawal exceeds available funds");
        }
        balance = newBalance;
    }

    public void deposit(Money amount) {
        balance = balance.plus(amount);
    }

}