package project.strategy;

/**
 * An interface representing a strategy for handling message queues.
 *
 * @param <T> The type of elements in the queue.
 */
public interface QueueStrategy<T> {

    /**
     * Sends a message to the specified source (queue, topic, etc.).
     *
     * @param message The message of type T to send.
     */
    void send(T message);

    /**
     * Receives a message from the specified source (queue, topic, etc.).
     *
     * @param source The source from which to receive messages.
     * @return The received message of type T.
     */
    T receive(String source);
}
