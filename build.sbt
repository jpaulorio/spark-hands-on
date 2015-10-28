lazy val commonSettings = Seq(
  name := "word-count-spark",
  version := "0.0.2",
  organization := "com.thoughtworks",
  scalaVersion := "2.10.4"
)

assemblyJarName in assembly := "word-count-spark.jar"
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

libraryDependencies ++= Seq(
    "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.2"
)

libraryDependencies ++= Seq(
    "org.apache.spark" % "spark-core_2.10" % "1.5.1" % "provided"
)

libraryDependencies ++= Seq(
    "org.apache.hadoop" % "hadoop-client" % "2.6.1" % "provided"
)