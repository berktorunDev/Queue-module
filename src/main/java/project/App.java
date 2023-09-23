package project;

import project.queue.KafkaQueue;
import project.queue.RabbitMQQueue;
import project.strategy.KafkaStrategy;
import project.strategy.QueueStrategy;
import project.strategy.RabbitMQStrategy;

/**
 * In this code:
 *
 * We are using the Factory design pattern to create instances of message queues
 * for Kafka and RabbitMQ.
 * The KafkaFactory and RabbitMQFactory classes are responsible for creating
 * instances of KafkaQueue and RabbitMQQueue, respectively.
 *
 * We are also using the Strategy design pattern to encapsulate the behavior of
 * sending and receiving messages for each message queue type.
 * The KafkaStrategy and RabbitMQStrategy classes provide a common interface for
 * interacting with these queues.
 *
 * The use of the "new" operator is essential in creating instances of the
 * concrete classes (KafkaQueue and RabbitMQQueue) as well as the strategy
 * classes (KafkaStrategy and RabbitMQStrategy).
 * This adheres to the Factory pattern, where factories are responsible for
 * object creation.
 *
 * By encapsulating the creation of objects and the strategies for message
 * handling, we achieve better code organization and flexibility.
 * This separation of concerns allows us to change message queue implementations
 * or strategies without impacting the rest of the application.
 */
public class App {
    public static void main(String[] args) {
        // Using KafkaStrategy for Kafka
        // Creating a Kafka queue using KafkaFactory
        QueueStrategy<Object> kafkaStrategy = new KafkaStrategy<>(new KafkaQueue<>("localhost:9092", "my-kafka-topic"));
        kafkaStrategy.send("Hello from Kafka!"); // Sending a message to Kafka
        String kafkaMessage = (String) kafkaStrategy.receive("my-kafka-topic"); // Receiving a message from Kafka
        System.out.println("Received from Kafka: " + kafkaMessage);

        // Using RabbitMQStrategy for RabbitMQ
        try {
            // Here, we create an instance of RabbitMQStrategy, which encapsulates a
            // RabbitMQQueue.
            // We use the "new" operator to instantiate the objects, adhering to the Factory
            // and Strategy design patterns.
            QueueStrategy<Object> rabbitMQStrategy = new RabbitMQStrategy<>(
                    new RabbitMQQueue<>("localhost:9095", "my-rabbitmq-queue"));
            rabbitMQStrategy.send("Hello from RabbitMQ!"); // Sending a message to RabbitMQ
            String rabbitMQMessage = (String) rabbitMQStrategy.receive("my-rabbitmq-queue"); // Receiving a message from
                                                                                             // RabbitMQ
            System.out.println("Received from RabbitMQ: " + rabbitMQMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
