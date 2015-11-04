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

    }

}