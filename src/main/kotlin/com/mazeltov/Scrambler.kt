package com.mazeltov

import kotlin.math.*

class Scrambler(private val inputImage: Array<IntArray>, private val kR: IntArray, private val kC: IntArray) {

    private val matrixSize: Int = inputImage.size
    private val matrixElementSize: Int = inputImage[0].size


    fun scramble(): Array<IntArray> {

        // scramble rows
        for (i in 0 until matrixSize) {
            var sumI = 0
            for (j in 0 until matrixElementSize) {
                sumI += inputImage[i][j]
            }
            if (sumI % 2 == 0) {
                shiftRowRight(i)
            } else {
                shiftRowLeft(i)
            }
        }

        // scramble columns
        for (j in 0 until matrixElementSize) {
            var sumJ = 0
            for (i in 0 until matrixSize) {
                sumJ += inputImage[i][j]
            }
            if (sumJ % 2 == 0) {
                shiftColumnUp(j)
            } else {
                shiftColumnDown(j)
            }
        }
        return inputImage
    }

    /**
     * Unscrambles the input matrix
     * @return the unscrambled matrix
     */
    fun unscramble(): Array<IntArray> {

        // unscramble columns
        for (j in 0 until matrixElementSize) {
            var sumJ = 0
            for (i in 0 until matrixSize) {
                sumJ += inputImage[i][j]
            }
            if (sumJ % 2 == 0) {
                shiftColumnDown(j)
            } else {
                shiftColumnUp(j)
            }
        }

        // unscramble rows
        for (i in 0 until matrixSize) {
            var sumI = 0
            for (j in 0 until matrixElementSize) {
                sumI += inputImage[i][j]
            }
            if (sumI % 2 == 0) {
                shiftRowLeft(i)
            } else {
                shiftRowRight(i)
            }
        }
        return inputImage
    }

    private fun shiftColumnDown(colNum: Int) {
        val shiftBy = abs(kC[colNum] % matrixSize)
        val temp = IntArray(matrixSize)
        for (i in 0 until matrixSize) {
            temp[i] = inputImage[(i - shiftBy + matrixSize) % matrixSize][colNum]
        }
        // copy
        for (i in 0 until matrixSize) {
            inputImage[i][colNum] = temp[i]
        }
    }

    private fun shiftColumnUp(colNum: Int) {
        val shiftBy = abs(kC[colNum] % matrixSize)
        val temp = IntArray(matrixSize)
        for (i in 0 until matrixSize) {
            temp[i] = inputImage[(i + shiftBy) % matrixSize][colNum]
        }
        // copy
        for (i in 0 until matrixSize) {
            inputImage[i][colNum] = temp[i]
        }
    }

    private fun shiftRowLeft(rowNum: Int) {
        val shiftBy = abs(kR[rowNum] % matrixElementSize)
        val temp = IntArray(matrixElementSize)
        for (i in 0 until matrixElementSize) {
            temp[i] = inputImage[rowNum][(i + shiftBy) % matrixElementSize]
        }
        // copy
        for (i in 0 until matrixElementSize) {
            inputImage[rowNum][i] = temp[i]
        }
    }


    private fun shiftRowRight(rowNum: Int) {
        val shiftBy = abs(kR[rowNum] % matrixElementSize)
        val temp = IntArray(matrixElementSize)
        for (i in 0 until matrixElementSize) {
            temp[i] = inputImage[rowNum][(i - shiftBy + matrixElementSize) % matrixElementSize]
        }
        // copy
        for (i in 0 until matrixElementSize) {
            inputImage[rowNum][i] = temp[i]
        }
    }

}
