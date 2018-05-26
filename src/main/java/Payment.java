import java.util.Map;

/**
 * Created by ola on 2018-05-26.
 */
public class Payment {

    private final Map<Integer, Integer> bankAccount;

    public Payment(Map<Integer, Integer> bankAccount) {
        this.bankAccount = bankAccount;
    }

    public boolean withdraw(int price, int id) {
       synchronized (bankAccount) {
           Integer balance = bankAccount.getOrDefault(id, 0);
           if (balance >= price) {
               bankAccount.put(id, balance - price);
               return true;
           }
           return false;
       }
    }

    public synchronized boolean deposit(int amount, int id) {
        synchronized (bankAccount) {
            Integer balance = bankAccount.getOrDefault(id, 0);
            bankAccount.put(id, balance + amount);
            return true;
        }
    }

    public int getBalance(int userId) {
        //TODO: Maybe optional is nicer?!
        return bankAccount.getOrDefault(userId, 0);
    }
}
