import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Created by ola on 2018-05-26.
 */
public class EventQueue {
    private Multimap<String, EventListerner> topics = ArrayListMultimap.create();



    public void register(String topic, EventListerner eventListerner) {
            topics.put(topic, eventListerner);
    }

    public void publish(String topic, Message message) {
        topics.get(topic).forEach(el -> el.onMessage(message));
    }
}
