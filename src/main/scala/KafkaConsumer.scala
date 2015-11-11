import kafka.serializer.StringDecoder

import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf

object KafkaConsumer {
  def main(args: Array[String]) {

    //replace "admin" with "alunoXX"
    val input = Array("prt01:9092,prt02:9092,prt03:9092","admin")
    val Array(brokers, topics) = input

    val sparkConf = new SparkConf().setAppName("KafkaWordCount")
    val ssc = new StreamingContext(sparkConf, Seconds(2))

    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicsSet)

    val lines = messages.map(_._2)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L)).reduceByKey(_ + _)
    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()

  }
}