import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import scalax.file.Path
import scalax.io._

object SparkSql {

    def main(args: Array[String]) {

        val appName = "Athletes"        
        val master = if (args.length > 2) args(2) else "local"

        val conf = new SparkConf().setAppName(appName).setMaster(master)
        val sparkContext = new SparkContext(conf)

        val sqlContext = new SQLContext(sparkContext)

        import sqlContext.implicits._

        val df = sqlContext.read.json("resources/people.json")

        // Displays the content of the DataFrame to stdout
        df.show()
        
        // Print the schema in a tree format
        df.printSchema()

        // Select only the "name" column
        df.select("name").show()

        // Select everybody, but increment the age by 1
        df.select(df("name"), df("age") + 1).show()

        // Select people older than 21
        df.filter(df("age") > 21).show()

        // Count people by age
        df.groupBy("age").count().show()


        val people = sparkContext.textFile("resources/people.txt").
        map(_.split(",")).
        map(p => Person(p(0), p(1).trim.toInt)).toDF()

        people.registerTempTable("people")

        val teenagers = 
        sqlContext.
        sql("SELECT name, age FROM people WHERE age >= 13 AND age <= 19")

        teenagers.map(t => "Name: " + t(0)).collect().foreach(println)

        teenagers.map(t => "Name: " + t.getAs[String]("name")).
        collect().foreach(println)

        teenagers.map(_.getValuesMap[Any](List("name", "age"))).
        collect().foreach(println)
    }

}