import java.util.function.Supplier;

public class ReversibleAction<T> {
    private final Supplier<T> action;
    private final Supplier<T> reverseAction;

    public ReversibleAction(Supplier<T> action, Supplier<T> reverseAction) {
        this.action = action;
        this.reverseAction = reverseAction;
    }

    public T execute() throws RollbackException {
        try {
            return action.get();
        } catch (Exception e) {
            throw new RollbackException();
        }
    }

    public T reverse() {
        return reverseAction.get();
    }
}
