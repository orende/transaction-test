import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ola on 2018-05-26.
 */
public class Transaction {
    private final List<ReversibleAction> actions;

    public Transaction(List<ReversibleAction> actions) {
        this.actions = actions;
    }

    public Transaction(ReversibleAction... actions) {
        this.actions = Arrays.asList(actions);
    }

    public boolean execute() {
        List<ReversibleAction> toBeExecuted = new ArrayList<>(actions);
        List<ReversibleAction> executed = new ArrayList<>();

        for (int i = 0; i < toBeExecuted.size(); i++) {
            try {
                toBeExecuted.get(i).execute();
                executed.add(toBeExecuted.get(i));
            } catch (RollbackException e) {
                rollback(executed);
                return false;
            }
        }
        return true;
    }

    private void rollback(List<ReversibleAction> executed) {
        for (int i = executed.size() - 1; i >= 0; i--) {
            executed.get(i).reverse(); //TODO this will never fail
        }
    }
}
