import java.util.UUID;

/**
 * Created by ola on 2018-05-26.
 */
public class Message {
    private String topic;
    private UUID transaction;

    public String getTopic() {
        return topic;
    }

    public UUID getTransaction() {
        return transaction;
    }
}
