package agents;

import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class AlertProducer {
   
   public static void main(String[] args) throws Exception{
      
      if(args.length == 0) {
         System.out.println("Please enter topic...");
         return;
      }
      
      //Assign topicName to string variable
      String topic = args[0].toString();
      Properties props = getProperties();
      Producer<String, String> producer = new KafkaProducer<String, String>(props);

      int i = 0;
      while(i < 3600) { // keep sending message for an hour
         String key =  Integer.toString(i  % 10);
         String value = Integer.toString(new Random().nextInt(10));
         System.out.println(String.format("Sending message=%d, key=%d, value=%d", i, key, value));
         producer.send(new ProducerRecord(topic, key, value));
         Thread.sleep(1000); // every 1 second
         i++;
      }

      producer.close();
   }

   // create instance for properties to access producer configs
   private static Properties getProperties() {
      Properties props = new Properties();
      props.put("bootstrap.servers", "localhost:9092");       // Assign localhost:port
      props.put("acks", "all");      // Set acknowledgements for producer requests.
      props.put("retries", 0);       // If the request fails, the producer can automatically retry,
      props.put("batch.size", 16384);       // Specify buffer size in config
      props.put("linger.ms", 1);       // Reduce the no of requests less than 0
      props.put("buffer.memory", 33554432);  // Total memory available to the producer for buffering.
      props.put("key.serializer", StringSerializer.class.getCanonicalName());
      props.put("value.serializer", StringSerializer.class.getCanonicalName());
      return props;
   }
}
