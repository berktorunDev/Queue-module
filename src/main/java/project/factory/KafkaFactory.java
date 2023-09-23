package project.factory;

import java.util.Queue;

import project.queue.KafkaQueue;

/**
 * Factory class for creating Kafka queues of a specified type.
 *
 * @param <T> The type of elements that the Kafka queue will hold.
 */
public class KafkaFactory<T> implements QueueFactory<T> {

    // The Kafka server address.
    private String serverAddress = "localhost:9092";

    // The name of the Kafka topic where messages will be produced and consumed.
    private String topicName = "YOUR_TOPIC_NAME";

    /**
     * Creates a new Kafka queue of the specified type.
     *
     * @return A Kafka queue instance.
     */
    @Override
    public Queue<T> createQueue() {
        // Create and return a new KafkaQueue with the configured server address and
        // topic name.
        return (Queue<T>) new KafkaQueue<>(serverAddress, topicName);
    }
}
