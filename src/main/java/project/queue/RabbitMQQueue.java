package project.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

/**
 * A class representing a RabbitMQ message queue for sending and receiving
 * messages.
 *
 * @param <T> The type of elements that the queue will hold.
 */
public class RabbitMQQueue<T> {

    private final ConnectionFactory factory;
    private final Connection connection;
    private final Channel channel;
    private final String queueName;

    /**
     * Constructs a RabbitMQQueue with the specified RabbitMQ host and queue name.
     *
     * @param host      The RabbitMQ host address.
     * @param queueName The name of the RabbitMQ queue.
     * @throws Exception If an error occurs during connection setup.
     */
    public RabbitMQQueue(String host, String queueName) throws Exception {
        this.queueName = queueName;
        this.factory = new ConnectionFactory();
        this.factory.setHost(host);

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
        this.channel.queueDeclare(queueName, false, false, false, null);
    }

    /**
     * Sends a message to the RabbitMQ queue.
     *
     * @param message The message of type T to send.
     * @throws Exception If an error occurs while sending the message.
     */
    public void sendMessage(T message) throws Exception {
        String messageBody = message.toString();
        channel.basicPublish("", queueName, null, messageBody.getBytes());
    }

    /**
     * Receives a message from the RabbitMQ queue.
     *
     * @param source The source (queue) from which to receive messages.
     * @return The received message of type T, or null if no message is available.
     * @throws Exception If an error occurs while receiving the message.
     */
    public T receive(String source) throws Exception {
        GetResponse response = channel.basicGet(source, true);
        if (response != null) {
            byte[] body = response.getBody();
            String message = new String(body, "UTF-8");
            // You can process the received message as needed here.
            // For example, perform JSON conversion or save to a database.
            // In the example below, we directly return the received message as a String:
            return (T) message;
        } else {
            // Return null if there are no messages in the queue.
            return null;
        }
    }

    /**
     * Closes the RabbitMQ channel and connection.
     *
     * @throws Exception If an error occurs while closing the channel or connection.
     */
    public void close() throws Exception {
        channel.close();
        connection.close();
    }
}
