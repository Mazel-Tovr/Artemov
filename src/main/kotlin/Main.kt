import com.mazeltov.*
import java.io.*
import java.security.*
import kotlin.math.*


//private const val FILE_FORMAT = ".jpg"
private const val KEY_EXT = ".key"
private const val ENC = "enc"
private const val DEC = "dec"

/**
 * PIXEL_SIZE is the size of each pixel of the image
 * PIXEL_MAX_VALUE is the number of possible values we could have with a pixel of PIXEL_SIZE
 */
private const val PIXEL_SIZE = 32
private val PIXEL_MAX_VALUE = 2.0.pow(PIXEL_SIZE.toDouble()).toInt()

/**
 * maxIterations is the number of iterations to be used by the algorithm. The more the number of iterations, the more secure the data becomes.
 */
private var maxIterations = 1

private val random = SecureRandom()

private const val errorMessage =
    "Unknown input 1 - command(enc/dec), 2 - path to image, 3 - path to key (if dec command )"
fun main(args: Array<String>) {

    when (args.size) {

        2 -> {
            args[0].takeIf { it == ENC } ?: print(errorMessage).also { return }
            val path = args[1]
            val fileExtension = ".${File(path).extension}"
            val inputFile = File(File(path).absolutePath)
            var inputImage: Array<IntArray> = ImageUtils.compute(inputFile)
            val M = inputImage.size
            val N: Int = inputImage[0].size

            val folderPath = File(path).parent
            val keyFile = File("$folderPath/" + inputFile.nameWithoutExtension + KEY_EXT)
            val kR = IntArray(M) { random.nextInt(PIXEL_MAX_VALUE) }
            val kC = IntArray(N) { random.nextInt(PIXEL_MAX_VALUE) }
            KeyUtils.writeKey(keyFile, kR, kC, maxIterations)
            for (i in 0 until maxIterations) {
                inputImage = Scrambler(inputImage, kR, kC).scramble()
                inputImage = encrypt(inputImage, kR, kC)
            }
            val encFile = File("$folderPath/" + inputFile.nameWithoutExtension + ENC + fileExtension)
            ImageUtils.saveImage(inputImage, encFile)
        }
        3 -> {
            args[0].takeIf { it == DEC } ?: print(errorMessage).also { return }
            val path = args[1]
            val fileExtension = ".${File(path).extension}"
            val inputFile = File(File(path).absolutePath)
            var inputImage: Array<IntArray> = ImageUtils.compute(inputFile)
            val M = inputImage.size
            val N: Int = inputImage[0].size

            val folderPath = File(path).parent

            val keyFile = File(args[2])
            val kR = IntArray(M)
            val kC = IntArray(N)
            maxIterations = KeyUtils.readKey(keyFile, kR, kC)
            for (i in 0 until maxIterations) {
                inputImage = decrypt(inputImage, kR, kC)
                inputImage = Scrambler(inputImage, kR, kC).unscramble()
            }
            val decFile = File("$folderPath/" + inputFile.nameWithoutExtension + DEC + fileExtension)
            ImageUtils.saveImage(inputImage, decFile)
        }
        else -> println(errorMessage)
    }
    println("PressButton")
    readLine()

}



