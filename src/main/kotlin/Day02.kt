import kotlin.math.abs

object Day02 {

    fun part1() {
        val input = parseInput()
        val result = input.count { isSafe(it) }
        println("day 2, part 1: $result")
    }

    fun part2() {
        val input = parseInput()
        val result = input.count { isSafeAllowingOutliers(it) }
        println("day 2, part 2: $result")
    }

    private fun parseInput(): List<List<Int>> {
        val rawInput = FileReader().readFile("input02.txt")
        return rawInput.trim().split("\n")
            .map { line ->
                line.split(Regex("\\s+")).filter { it.isNotBlank() }.map { it.toInt() }
            }
    }

    private fun isSafe(list: List<Int>): Boolean {
        val deltas = (1..<list.size).map { i ->
            when {
                list[i] > list[i - 1] -> 1
                list[i] < list[i - 1] -> -1
                else -> 0
            }
        }
        val diffs = (1..<list.size).map { i ->
            abs(list[i] - list[i - 1])
        }
        val deltaSafe = deltas.distinct().size == 1
        val diffSafe = diffs.all { it in 1..3 }

        return deltaSafe && diffSafe
    }

    private fun isSafeAllowingOutliers(list: List<Int>): Boolean {
        return (isSafe(list)) ||
                list.indices.any { i ->
                    isSafe(list.subList(0, i) + list.subList(i + 1, list.size))
                }
    }

}

private const val TEST_INPUT = """
7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
"""