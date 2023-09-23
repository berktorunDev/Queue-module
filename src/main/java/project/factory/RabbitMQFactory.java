package project.factory;

import java.util.Queue;

import project.queue.RabbitMQQueue;

/**
 * A factory class for creating RabbitMQ queues.
 *
 * @param <T> The type of elements that the queues created by this factory will
 *            hold.
 */
public class RabbitMQFactory<T> implements QueueFactory<T> {

    private String serverAddress = "localhost:9095";
    private String queueName = "QUEUE_NAME";

    @Override
    public Queue<T> createQueue() {
        try {
            // Create and return a RabbitMQQueue instance.
            return (Queue<T>) new RabbitMQQueue<>(serverAddress, queueName);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating a RabbitMQ connection.", e);
        }
    }
}
