object Day07 {

    fun part1() {
        val input = FileReader().readFile("input07.txt")
        val lines = parseInput(input)
        val result = lines.filter { recursePart1(it.target, 0, it.operands) }.sumOf { it.target }

        println("day 7, part 1: $result")
    }

    fun part2() {
        val input = FileReader().readFile("input07.txt")
        val lines = parseInput(input)
        val result = lines.filter { recursePart2(it.target, 0, it.operands) }.sumOf { it.target }

        println("day 7, part 2: $result")
    }

    private data class InputLine(val target: Long, val operands: List<Long>)

    private fun parseInput(input: String): List<InputLine> =
        input.split("\n").filter { it.isNotBlank() }
            .map { it.split(":").filter { part -> part.isNotBlank() } }
            .map {
                InputLine(
                    target = it[0].trim().toLong(),
                    operands = it[1].trim().split(Regex("\\s+")).map { c -> c.toLong() }
                )
            }

    private fun recursePart1(target: Long, total: Long, operands: List<Long>): Boolean {
        if (total > target) return false
        if (operands.isEmpty()) {
            return total == target
        }
        val plus = recursePart1(target, total + operands[0], operands.subList(1, operands.size))
        val times = recursePart1(target, total * operands[0], operands.subList(1, operands.size))
        return plus || times
    }

    private fun recursePart2(target: Long, total: Long, operands: List<Long>): Boolean {
        if (total > target) return false
        if (operands.isEmpty()) {
            return total == target
        }
        val plus = recursePart2(target, total + operands[0], operands.subList(1, operands.size))
        val times = recursePart2(target, total * operands[0], operands.subList(1, operands.size))
        val concat = recursePart2(target, (total.toString() + operands[0].toString()).toLong(), operands.subList(1, operands.size))
        return plus || times || concat
    }
}

private const val TEST_INPUT = """
190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20
"""