# Queue-module
# Message Queue Abstraction with Factory and Strategy Design Patterns

This project demonstrates the use of Factory and Strategy design patterns in building an abstraction for message queues. Message queues are a fundamental part of modern software systems, and having a flexible and extensible way to interact with different message queue providers is essential.

## Factory Design Pattern

The Factory design pattern is employed to create instances of message queues for Kafka and RabbitMQ. Two factory classes, `KafkaFactory` and `RabbitMQFactory`, are responsible for creating instances of `KafkaQueue` and `RabbitMQQueue`, respectively.

### `KafkaFactory<T>` Class

The `KafkaFactory` class is responsible for creating Kafka queues. It allows you to specify the server address and topic name when creating a Kafka queue.

### `RabbitMQFactory<T>` Class

The `RabbitMQFactory` class is responsible for creating RabbitMQ queues. It allows you to specify the server address and queue name when creating a RabbitMQ queue.

## Strategy Design Pattern

The Strategy design pattern is used to encapsulate the behavior of sending and receiving messages for each message queue type (Kafka and RabbitMQ). Two strategy classes, `KafkaStrategy` and `RabbitMQStrategy`, provide a common interface for interacting with these queues.

### `KafkaStrategy<T>` Class

The `KafkaStrategy` class encapsulates the behavior of sending and receiving messages for Kafka queues. It uses a `KafkaQueue` instance to interact with Kafka.

### `RabbitMQStrategy<T>` Class

The `RabbitMQStrategy` class encapsulates the behavior of sending and receiving messages for RabbitMQ queues. It uses a `RabbitMQQueue` instance to interact with RabbitMQ.

## Classes and Interfaces

- `QueueFactory<T>` (Interface): An interface that defines a factory for creating message queues.
- `KafkaFactory<T>` (Class): A concrete factory class for creating Kafka queues.
- `RabbitMQFactory<T>` (Class): A concrete factory class for creating RabbitMQ queues.
- `KafkaQueue<T>` (Class): A class representing a Kafka message queue.
- `RabbitMQQueue<T>` (Class): A class representing a RabbitMQ message queue.
- `QueueStrategy<T>` (Interface): An interface that defines the strategy for sending and receiving messages.
- `KafkaStrategy<T>` (Class): A concrete strategy class for interacting with Kafka queues.
- `RabbitMQStrategy<T>` (Class): A concrete strategy class for interacting with RabbitMQ queues.

## Getting Started

To use this message queue abstraction, follow these steps:

1. Create instances of `KafkaFactory` or `RabbitMQFactory` to get message queue instances.
2. Use the obtained queue instances to send and receive messages.
3. Use `KafkaStrategy` or `RabbitMQStrategy` to define the message handling strategy for the corresponding queue type.

## Example Usage

To illustrate how the Factory and Strategy design patterns work together in managing message queues, consider the following Java code:

```java
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

