package Kafka.customer

import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer, OffsetAndMetadata, OffsetCommitCallback}
import org.apache.kafka.common.TopicPartition
import org.apache.log4j.Logger
object ConsumerApi {
  private val log = Logger.getLogger(ConsumerApi.getClass)
  def main(args:Array[String]):Unit = {
    val topic: String = "test2"
    val topicList = new util.ArrayList[String]
    topicList.add(topic)

    val consumerPros = new Properties()
    consumerPros.put("bootstrap.servers", "192.168.113.132:9092")
    consumerPros.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    consumerPros.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    consumerPros.put("group.id", "Customer")

    consumerPros.put("enable.auto.commit", "false")
    consumerPros.put("auto.commit.interval.ms", "1000")
    consumerPros.put("session.timeout.ms", "30000")

    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](consumerPros)
    consumer.subscribe(topicList)
    log.info("Subscribed to topic " + topic)

    try {
      while (true) {
        val records: ConsumerRecords[String, String] = consumer.poll(2)
        import scala.collection.JavaConversions._
        for (record <- records) {
          val info = s"offset = ${record.offset()}, key = ${record.key()}, value = ${record.value()}"
          log.info(info+"\n")
         System.out.println(info)
        }
        consumer.commitAsync(new OffsetCommitCallback {
          override def onComplete(offsets: util.Map[TopicPartition, OffsetAndMetadata], exception: Exception): Unit = {
            if (exception != null) exception.printStackTrace()
          }
        })
      }
    } catch {
      case ex: Exception => ex.printStackTrace()
    }finally {
      try{
        consumer.commitSync()
      }finally {
        consumer.close()
      }
    }
  }
}
