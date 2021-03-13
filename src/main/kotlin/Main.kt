import com.mazeltov.*
import java.io.*
import java.security.*
import kotlin.math.*

private const val FILE_FORMAT = ".jpg"
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

/** CHANGE THIS **/
private val command = ENC

private const val imageName = "cyberpunk-2077"

private const val sourcePath = "src/main/resources"

private val picturePath =  "$sourcePath/source/$imageName$FILE_FORMAT".takeIf { command == ENC } ?: "$sourcePath/encode/${imageName}enc$FILE_FORMAT"

private const val keyPath = "$sourcePath/key/$imageName.key"


fun main(args: Array<String>) {

    val inputFile = File(File(picturePath).absolutePath)
    var inputImage: Array<IntArray> = ImageUtils.compute(inputFile)
    val M = inputImage.size
    val N: Int = inputImage[0].size
    val kR: IntArray
    val kC: IntArray
    val keyFile: File
    if (command == DEC) {

        keyFile = File(keyPath)
        kR = IntArray(M)
        kC = IntArray(N)
        maxIterations = KeyUtils.readKey(keyFile, kR, kC)
        for (i in 0 until maxIterations) {
            inputImage = decrypt(inputImage, kR, kC)
            inputImage = Scrambler(inputImage, kR, kC).unscramble()
        }
        val decFile = File("$sourcePath/decode/" + inputFile.nameWithoutExtension + DEC + FILE_FORMAT)
        ImageUtils.saveImage(inputImage, decFile)
    } else {
        keyFile = File("$sourcePath/key/" + inputFile.nameWithoutExtension + KEY_EXT)
        kR = IntArray(M) { random.nextInt(PIXEL_MAX_VALUE) }
        kC = IntArray(N) { random.nextInt(PIXEL_MAX_VALUE) }
        KeyUtils.writeKey(keyFile, kR, kC, maxIterations)
        for (i in 0 until maxIterations) {
            inputImage = Scrambler(inputImage, kR, kC).scramble()
            inputImage = encrypt(inputImage, kR, kC)
        }
        val encFile = File("$sourcePath/encode/" + inputFile.nameWithoutExtension + ENC + FILE_FORMAT)
        ImageUtils.saveImage(inputImage, encFile)
    }
}



