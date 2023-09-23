package project.queue;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * A class representing a Kafka message queue for sending and receiving
 * messages.
 *
 * @param <T> The type of elements that the queue will hold.
 */
public class KafkaQueue<T> {

    private final Producer<String, T> producer;
    private final String topic;

    /**
     * Constructs a KafkaQueue with the specified bootstrap servers and topic.
     *
     * @param bootstrapServers The Kafka bootstrap servers in the format
     *                         "host:port".
     * @param topic            The Kafka topic to which messages will be sent and
     *                         received.
     */
    public KafkaQueue(String bootstrapServers, String topic) {
        this.topic = topic;

        // Configure Kafka producer settings
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        producer = new KafkaProducer<>(producerProps);
    }

    /**
     * Sends a message to the Kafka topic.
     *
     * @param message The message of type T to send.
     */
    public void sendMessage(T message) {
        // Send the message to the Kafka topic
        ProducerRecord<String, T> record = new ProducerRecord<>(topic, message);
        producer.send(record);
    }

    /**
     * Receives a message from the Kafka topic.
     *
     * @param source The source (topic) from which to receive messages.
     * @return The received message of type T.
     */
    public T receive(String source) {
        // Create a Kafka consumer for receiving messages
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Kafka server address
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String, T> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList(source)); // Subscribe to the specified topic

        // Consume and process messages
        try {
            while (true) {
                ConsumerRecords<String, T> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, T> record : records) {
                    // Process the received message
                    T message = record.value();
                    // Perform message processing
                    return message;
                }
            }
        } finally {
            // Close the consumer when done
            consumer.close();
        }
    }

    /**
     * Closes the Kafka producer.
     */
    public void close() {
        producer.close();
    }
}
