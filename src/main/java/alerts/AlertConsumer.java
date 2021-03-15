package alerts;

import java.util.Properties;
import java.util.Arrays;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class AlertConsumer {

    public static void main(String[] args) throws Exception {
        if(args.length == 0){
            System.out.println("Enter topic name:");
            return;
        }

        //Kafka consumer configuration settings
        String topicName = args[0];
        Properties props = getProperties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "groupA");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        //Kafka Consumer subscribes list of topics here.
        consumer.subscribe(Arrays.asList(topicName));
        System.out.println("Subscribed to topic " + topicName);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset=%d, key=%s, value=%s\n", record.offset(), record.key(), record.value());
                Integer metric = Integer.parseInt(record.value());
                if ( metric > 9 ) { // 10% messages will be alerted
                    System.err.println("!!! Alert, Alert, Alert !!!: " + metric);
                }

            }
        }

    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "groupA");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.serializer", StringSerializer.class.getCanonicalName());
        props.put("value.serializer", StringSerializer.class.getCanonicalName());
        return props;
    }

}
