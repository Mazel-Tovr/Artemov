package com.mazeltov

import java.awt.image.*
import java.io.*
import javax.imageio.*

object ImageUtils {

    fun compute(file: File): Array<IntArray> {
        val img = ImageIO.read(file)
        val w = img.width
        val h = img.height
        val pixels = Array(w) { IntArray(h) }
        for (x in 0 until w) {
            for (y in 0 until h) {
                pixels[x][y] = img.getRGB(x, y)
            }
        }
        return pixels
    }


    fun saveImage(pixels: Array<IntArray>, output: File) {
        val w = pixels.size
        val h: Int = pixels[0].size
        val image = BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
        for (i in 0 until w) {
            for (j in 0 until h) {
                image.setRGB(i, j, pixels[i][j])
            }
        }
        runCatching {
            ImageIO.write(image, "png", output)
        }
    }
}
