package project.strategy;

import project.queue.RabbitMQQueue;

/**
 * A strategy for handling message queues using RabbitMQ.
 *
 * @param <T> The type of elements in the queue.
 */
public class RabbitMQStrategy<T> implements QueueStrategy<T> {
    private RabbitMQQueue<T> rabbitMQQueue;

    /**
     * Creates a new RabbitMQStrategy with the specified RabbitMQQueue.
     *
     * @param rabbitMQQueue The RabbitMQQueue to use for sending and receiving
     *                      messages.
     */
    public RabbitMQStrategy(RabbitMQQueue<T> rabbitMQQueue) {
        this.rabbitMQQueue = rabbitMQQueue;
    }

    /**
     * Sends a message to the RabbitMQ queue.
     *
     * @param message The message of type T to send.
     */
    @Override
    public void send(T message) {
        try {
            rabbitMQQueue.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Receives a message from the RabbitMQ queue.
     *
     * @param source The source (queue) from which to receive messages.
     * @return The received message of type T, or null if an error occurs.
     */
    @Override
    public T receive(String source) {
        try {
            return rabbitMQQueue.receive(source);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
