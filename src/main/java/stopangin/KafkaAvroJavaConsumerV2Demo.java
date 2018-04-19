package stopangin;

import com.example.Customer;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class KafkaAvroJavaConsumerV2Demo {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","192.168.99.100:9092");
        properties.put("group.id", "customer-consumer-group-v2");
        properties.put("auto.commit.enable", "false");
        properties.put("auto.offset.reset", "earliest");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", KafkaAvroDeserializer.class.getName());
        properties.setProperty("schema.registry.url", "http://192.168.99.100:8081");
        properties.setProperty("specific.avro.reader", "true");

        KafkaConsumer kafkaConsumer = new KafkaConsumer<>(properties);
        String topic = "customer-avro";
        kafkaConsumer.subscribe(Collections.singleton(topic));


        while (true){
            ConsumerRecords records = kafkaConsumer.poll(1000);

            for (Object record : records){
                Customer customer =(Customer) ((ConsumerRecord) record).value();
                System.out.println(customer);
            }

            kafkaConsumer.commitSync();
        }
    }
}
