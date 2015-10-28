import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import scalax.file.Path
import scalax.io._

object Athletes {

    def main(args: Array[String]) {

        val appName = "Athletes"        
        val inputFile = args(0)
        val outputFile = args(1)
        val master = if (args.length > 2) args(2) else "local"

        val path: Path = Path (outputFile)        
        path.deleteRecursively(continueOnFailure = false)

        val conf = new SparkConf().setAppName(appName).setMaster(master)
        val sparkContext = new SparkContext(conf)

        val file = sparkContext.textFile(inputFile)

        val playerAndSport = file.map(x => x.split(",")).map(x => (x(0) + "-" + x(1), x(3)))

        val reduced = playerAndSport.reduceByKey((x, y) => x + "," + y)

        val playerAndSportArray = reduced.map(x => (x._1, x._2.split(",").toSet))

        val filtered = playerAndSportArray.filter(x => x._2.size > 1)

        val result = filtered.map(x => (x._1, x._2.size))

        result.saveAsTextFile(outputFile)

    }
}