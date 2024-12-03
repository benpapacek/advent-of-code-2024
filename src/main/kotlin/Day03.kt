object Day03 {

    fun part1() {
        val rawInput = FileReader().readFile("input03.txt")
        val result = calculateResult(rawInput)
        println("day 3, part 1: $result")
    }

    fun part2() {
        val rawInput = FileReader().readFile("input03.txt")
        val filteredInput = Regex("don't\\(\\)(.|\\n)*?(do\\(\\)|$)").replace(rawInput, "")
        val result = calculateResult(filteredInput)
        println("day 3, part 2: $result")
    }

    private fun calculateResult(s: String): Int {
        val matches = Regex("mul\\(\\d+,\\d+\\)").findAll(s).map { it.value }
        val result = matches.map { m ->
            Regex("\\d+").findAll(m).map { it.value.toInt() }.toList().let { it[0] * it[1] }
        }.sum()
        return result
    }
}

private const val TEST_INPUT_1 = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"

private const val TEST_INPUT_2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
