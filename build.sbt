lazy val commonSettings = Seq(
  name := "word-count-spark",
  version := "0.0.2",
  organization := "com.thoughtworks",
  scalaVersion := "2.10.4"
)

assemblyJarName in assembly := "word-count-spark.jar"
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
mainClass in (Compile, packageBin) := Some("KafkaWordProducer")

libraryDependencies ++= Seq(
    "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.2",
    "org.apache.spark" % "spark-core_2.10" % "1.5.1" % "provided",
    "org.apache.hadoop" % "hadoop-client" % "2.6.1" % "provided",
    "org.apache.spark" % "spark-streaming_2.10" % "1.5.1" % "provided",
    "org.apache.spark" % "spark-sql_2.10" % "1.5.1" % "provided",
    "org.apache.spark" % "spark-mllib_2.10" % "1.5.1" % "provided",
    "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.5.1" exclude("org.spark-project.spark", "unused")
)