import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import scalax.file.Path
import scalax.io._

object WordCountSpark {

    def main(args: Array[String]) {

        val appName = "Word-Count"        
        val inputFile = args(0)
        val outputFile = args(1)
        val master = if (args.length > 2) args(2) else "local"

        val path: Path = Path (outputFile)        
        path.deleteRecursively(continueOnFailure = false)

        val conf = new SparkConf().setAppName(appName).setMaster(master)
        val sparkContext = new SparkContext(conf)

        val file = sparkContext.textFile(inputFile)

        val counts = file.flatMap(line => line.split(" "))
            .map(word => (word, 1))
            .reduceByKey((a, b) => a + b)

        counts.saveAsTextFile(outputFile)

    }
}