name := "chap02"

version := "0.1"

scalaVersion := "2.12.13"

libraryDependencies ++= Seq(
  "org.apache.kafka" %%  "kafka"  % "2.7.0",
  "org.apache.kafka" %  "kafka-clients"  % "2.7.0",
  "log4j"  % "log4j"  % "1.2.17",
  "org.mongodb" %% "casbah" % "3.1.1",
  "org.apache.spark"  %% "spark-core"  %  "3.0.1" % "provided",
  "org.apache.spark"  %% "spark-streaming" % "3.0.1",
)