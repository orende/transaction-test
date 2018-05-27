import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by ola on 2018-05-26.
 */
public class Transaction {
    private final List<ReversibleAction> actions;
    private final CompletableFuture<Boolean> finishCallback;

    public Transaction(ReversibleAction... actions) {
        this.actions = Arrays.asList(actions);
        this.finishCallback = new CompletableFuture<>();
    }

    public CompletableFuture<Boolean> execute() {
        List<ReversibleAction> toBeExecuted = new ArrayList<>(actions);
        List<ReversibleAction> executed = new ArrayList<>();

        for (int i = 0; i < toBeExecuted.size(); i++) {
            try {
                toBeExecuted.get(i).execute();
                executed.add(toBeExecuted.get(i));
            } catch (RollbackException e) {
                rollback(executed);
                return finishCallback.complete(false);
            }
        }
        return finishCallback.complete(true);
    }

    private void rollback(List<ReversibleAction> executed) {
        for (int i = executed.size() - 1; i >= 0; i--) {
            executed.get(i).reverse(); //TODO this will never fail
        }
    }

    public CompletableFuture<Boolean> getFinishCallback() {
        return finishCallback;
    }
}
