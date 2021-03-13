package com.mazeltov

fun encrypt(matrix: Array<IntArray>, kR: IntArray, kC: IntArray): Array<IntArray> {

    val rotKR: IntArray = kR.reversedArray()
    val rotKC: IntArray = kC.reversedArray()
    val matrixSize = matrix.size
    val matrixElementSize: Int = matrix[0].size

    for (i in 0 until matrixSize step 2) {
        for (j in 0 until matrixElementSize) {
            matrix[i][j] = matrix[i][j] xor rotKC[j]
            if (i + 1 < matrixSize) {
                matrix[i + 1][j] = matrix[i + 1][j] xor kC[j]
            }
        }
    }

    for (i in 0 until matrixSize) {
        for (j in 0 until matrixElementSize step 2) {
            matrix[i][j] = matrix[i][j] xor rotKR[i]
            if (j + 1 < matrixElementSize) {
                matrix[i][j + 1] = matrix[i][j + 1] xor kR[i]
            }
        }
    }
    return matrix
}

fun decrypt(matrix: Array<IntArray>, kR: IntArray, kC: IntArray): Array<IntArray> {

    val rotKR: IntArray = kR.reversedArray()
    val rotKC: IntArray = kC.reversedArray()

    val matrixSize = matrix.size
    val matrixElementSize: Int = matrix[0].size

    for (i in 0 until matrixSize) {
        for (j in 0 until matrixElementSize step 2) {
            matrix[i][j] = matrix[i][j] xor rotKR[i]
            if (j + 1 < matrixElementSize) {
                matrix[i][j + 1] = matrix[i][j + 1] xor kR[i]
            }
        }
    }

    for (i in 0 until matrixSize step 2) {
        for (j in 0 until matrixElementSize) {
            matrix[i][j] = matrix[i][j] xor rotKC[j]
            if (i + 1 < matrixSize) {
                matrix[i + 1][j] = matrix[i + 1][j] xor kC[j]
            }
        }
    }
    return matrix
}
