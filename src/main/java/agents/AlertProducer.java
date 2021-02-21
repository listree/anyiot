package agents;

import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class AlertProducer {
   
   public static void main(String[] args) throws Exception{
      
      if(args.length == 0) {
         System.out.println("Please enter topic...");
         return;
      }
      
      //Assign topicName to string variable
      String topic = args[0].toString();

      // create instance for properties to access producer configs
      Properties props = new Properties();
      props.put("bootstrap.servers", "localhost:9092");       //Assign localhost id
      props.put("acks", "all");      //Set acknowledgements for producer requests.
      props.put("retries", 0);       //If the request fails, the producer can automatically retry,
      props.put("batch.size", 16384);       //Specify buffer size in config
      props.put("linger.ms", 1);       //Reduce the no of requests less than 0
      props.put("buffer.memory", 33554432);  //total memory available to the producer for buffering.
      props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
      props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
      Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(props);

      int i = 0;
      while(i < 3600) { // keep sending message for an hour
         System.out.println("Sending " + i++ + "th Message");
         String key =  Integer.toString(i  % 10);
         String value = Integer.toString(new Random().nextInt(10));
         producer.send(new ProducerRecord<String, String>(topic, key, value));
         Thread.sleep(1000); // every 1 second
      }

      producer.close();
   }
}
