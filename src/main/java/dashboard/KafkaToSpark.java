package dashboard;

import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;

import scala.Tuple2;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

/**
 * Consumes messages from one or more topics in Kafka and does wordcount.
 *
 * Usage: SparkDashboard <zkQuorum> <group> <topics> <numThreads>
 *   <zkQuorum> is a list of one or more zookeeper servers that make quorum
 *   <group> is the name of kafka consumer group
 *   <topics> is a list of one or more kafka topics to consume from
 *   <numThreads> is the number of threads the kafka consumer should use
 *
 * To run this example:
 *   `$ spark-submit SparkDashboard zoo01,zoo02, \
 *    zoo03 my-consumer-group topic1,topic2 1`
 */

public final class KafkaToSpark {

  private static final Pattern SPACE = Pattern.compile(" ");

  private KafkaToSpark() {
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 4) {
      System.err.println("Usage: SparkDashboard <zkQuorum> <group> <topics> <numThreads>");
      System.exit(1);
    }

    String zkQuorum = args[0];
    String groupId = args[1];
    String[] topics = args[2].split(",");
    int numThreads = Integer.parseInt(args[3]);

    // StreamingExamples.setStreamingLogLevels();
    SparkConf sparkConf = new SparkConf().setAppName("SparkDashboard");

    // Create the context with 2 seconds batch size
    JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, new Duration(2000));

    Map<String, Integer> topicMap = new HashMap<>();
    for (String topic: topics) {
      topicMap.put(topic, numThreads);
    }
    JavaPairReceiverInputDStream<String, String> messages =
            KafkaUtils.createStream(jssc, zkQuorum, groupId, topicMap);

    JavaDStream<String> lines = messages.map(Tuple2::_2);
    System.out.println(lines);

//    JavaDStream<Object> words =
//        lines.flatMap(string -> Arrays.asList(SPACE.split(string)).iterator());

//    JavaPairDStream<String, Integer> wordCounts =
//      words.mapToPair(s -> new Tuple2<>(s, 1)).reduceByKey((i1, i2) -> i1 + i2);

//    wordCounts.print();

    jssc.start();
    jssc.awaitTermination();
  }
}
