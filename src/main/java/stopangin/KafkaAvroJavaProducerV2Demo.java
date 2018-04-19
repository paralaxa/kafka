package stopangin;

import com.example.Customer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaAvroJavaProducerV2Demo {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.99.100:9092");
        properties.setProperty("acks", "all");
        properties.setProperty("retries", "10");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://192.168.99.100:8081");

        Producer<String, Customer> producer = new KafkaProducer<String, Customer>(properties);

        String topic = "customer-avro";


        Customer customer = Customer.newBuilder()
                .setAge(34)
                .setFirstName("DeepPurple")
                .setLastName("Gajaka")
                .setHeight(178f)
                .setWeight(75f)
                .setEmail("email@mail.sk")
                .setPhoneNumber("1234567890")
                .build();

        ProducerRecord<String, Customer> producerRecord = new ProducerRecord<String, Customer>(
                topic, customer
        );

        System.out.println(customer);
        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    System.out.println(metadata);
                } else {
                    exception.printStackTrace();
                }
            }
        });

        producer.flush();
        producer.close();

    }
}
