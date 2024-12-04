object Day04 {

    private const val XMAS = "XMAS"
    private const val XMAS_BACK = "SAMX"

    fun part1() {
        val input = FileReader().readFile("input04.txt")
        val a = input.split("\n").filter { it.isNotBlank() }.map { it.trim().toCharArray() }
        var count = 0
        a.indices.forEach { i ->
            a[i].indices.forEach { j ->
                if (matchHorizontalPart1(a, i, j, XMAS)) count++
                if (matchHorizontalPart1(a, i, j, XMAS_BACK)) count++
                if (matchVerticalPart1(a, i, j, XMAS)) count++
                if (matchVerticalPart1(a, i, j, XMAS_BACK)) count++
                if (matchDiagonalTopLeftToBottomRightPart1(a, i, j, XMAS)) count++
                if (matchDiagonalTopLeftToBottomRightPart1(a, i, j, XMAS_BACK)) count++
                if (matchDiagonalTopRightToBottomLeftPart1(a, i, j, XMAS)) count++
                if (matchDiagonalTopRightToBottomLeftPart1(a, i, j, XMAS_BACK)) count++
            }
        }
        println("day 4, part 1: $count")
    }

    private fun matchHorizontalPart1(a: List<CharArray>, i: Int, j: Int, s: String): Boolean =
        (j > 2 && a[i][j] == s[0] && a[i][j-1] == s[1] && a[i][j-2] == s[2] && a[i][j-3] == s[3])

    private fun matchVerticalPart1(a: List<CharArray>, i: Int, j: Int, s: String): Boolean =
        (i > 2 && a[i][j] == s[0] && a[i-1][j] == s[1] && a[i-2][j] == s[2] && a[i-3][j] == s[3])

    private fun matchDiagonalTopLeftToBottomRightPart1(a: List<CharArray>, i: Int, j: Int, s: String): Boolean =
        (i > 2 && j > 2 && a[i][j] == s[0] && a[i-1][j-1] == s[1] && a[i-2][j-2] == s[2] && a[i-3][j-3] == s[3])

    private fun matchDiagonalTopRightToBottomLeftPart1(a: List<CharArray>, i: Int, j: Int, s: String): Boolean =
        (i > 2 && j < a[i].size - 3 && a[i][j] == s[0] && a[i-1][j+1] == s[1] && a[i-2][j+2] == s[2] && a[i-3][j+3] == s[3])

    fun part2() {
        val input = FileReader().readFile("input04.txt")
        val a = input.split("\n").filter { it.isNotBlank() }.map { it.trim().toCharArray() }

        var count = 0
        (1 until a.size - 1).forEach { i ->
            (1 until a[i].size - 1).forEach { j ->
                if (a[i][j] == 'A' &&
                    matchDiagonalTopLeftToBottomRightPart2(a, i, j) &&
                    matchDiagonalTopRightToBottomLeftPart2(a, i, j)
                ) {
                    count++
                }
            }
        }

        println("day 4, part 2: $count")
    }

    private fun matchDiagonalTopLeftToBottomRightPart2(a: List<CharArray>, i: Int, j: Int) =
        ((a[i - 1][j - 1] == 'M' && a[i + 1][j + 1] == 'S') || (a[i - 1][j - 1] == 'S' && a[i + 1][j + 1] == 'M'))

    private fun matchDiagonalTopRightToBottomLeftPart2(a: List<CharArray>, i: Int, j: Int): Boolean =
        ((a[i - 1][j + 1] == 'M' && a[i + 1][j - 1] == 'S') || (a[i - 1][j + 1] == 'S' && a[i + 1][j - 1] == 'M'))

}

private const val TEST_INPUT = """
MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX
"""
