package project.strategy;

import project.queue.KafkaQueue;

/**
 * A strategy for handling message queues using Apache Kafka.
 *
 * @param <T> The type of elements in the Kafka queue.
 */
public class KafkaStrategy<T> implements QueueStrategy<T> {
    private KafkaQueue<T> kafkaQueue;

    /**
     * Constructs a KafkaStrategy with the specified KafkaQueue instance.
     *
     * @param kafkaQueue The KafkaQueue instance to use for message handling.
     */
    public KafkaStrategy(KafkaQueue<T> kafkaQueue) {
        this.kafkaQueue = kafkaQueue;
    }

    /**
     * Sends a message to the Kafka queue.
     *
     * @param message The message of type T to send.
     */
    @Override
    public void send(T message) {
        kafkaQueue.sendMessage(message);
    }

    /**
     * Receives a message from the Kafka queue.
     *
     * @param source The source (topic) from which to receive messages.
     * @return The received message of type T.
     */
    @Override
    public T receive(String source) {
        return kafkaQueue.receive(source);
    }
}
