import kotlin.math.abs

object Day01 {

    fun part1() {
        val (list1, list2) = parseInput()
        val list1Sorted = list1.sorted()
        val list2Sorted = list2.sorted()
        val result = list1Sorted.indices.sumOf { i ->
            abs(list1Sorted[i] - list2Sorted[i])
        }
        println("day 1, part 1: $result")
    }

    fun part2() {
        val (list1, list2) = parseInput()
        val result = list1.sumOf { n ->
            n * list2.count { it == n }
        }

        println("day 1, part 2: $result")
    }

    private fun parseInput(): Pair<List<Int>, List<Int>> {
        val rawInput = FileReader().readFile("input01.txt")
        val input = rawInput.split(Regex("\\s+")).filter { it.isNotBlank() }.map { it.toInt() }
        val list1 = input.filterIndexed { i, _ -> i % 2 == 0}
        val list2 = input.filterIndexed { i, _ -> i % 2 == 1}
        return list1 to list2
    }
}

private const val TEST_INPUT = """
    3   4
    4   3
    2   5
    1   3
    3   9
    3   3
"""
