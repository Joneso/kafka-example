package Kafka.customer.product
import java.util.Properties

import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
object ProducerDemo {
  def main(args: Array[String]): Unit = {
    val producerProps = new Properties()
    producerProps.put("bootstrap.servers", "192.168.113.132:9092")
    producerProps.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
    producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    producerProps.put("client.id", "SampleProducer")
    producerProps.put("acks", "all")

    producerProps.put("retries", new Integer(1))
    producerProps.put("batch.size", new Integer(16384))
    producerProps.put("linger.ms", new Integer(1))
    producerProps.put("buffer.memory", new Integer(133554432))

    val producer = new KafkaProducer[String, String](producerProps)
    for(a <- 1 to 2000){
      val record: ProducerRecord[String, String] = new ProducerRecord[String, String]("test2", s"Hello this is record ${a}")
      producer.send(record, new Callback{
        override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
          if(metadata != null)  println("message has been sent successfully! ")
          if(exception != null) exception.printStackTrace()
        }
      })
    }
    producer.close()
  }
}
