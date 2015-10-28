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

        

        result.saveAsTextFile(outputFile)

    }
}