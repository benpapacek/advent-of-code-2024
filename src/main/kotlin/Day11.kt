object Day11 {

    fun part1() {
        val input = FileReader().readFile("input11.txt")
        var a = input.trim().split(Regex("\\s+")).map { it.toLong() }
        repeat(25) {
            a = transform(a)
        }
        println("day 11, part 1: ${a.size}")
    }

    fun part2() {
        println("day 11, part 2: ?")
    }

    private fun transform(a: List<Long>): List<Long> {
        val b = mutableListOf<Long>()

        a.forEach { n ->
            if (n == 0L) {
                b.add(1)
            } else if (n.toString().length % 2 == 0) {
                val s = n.toString()
                b.add(s.substring(0, s.length / 2).toLong())
                b.add(s.substring(s.length / 2, s.length).toLong())
            } else {
                b.add(n * 2024L)
            }
        }
        return b
    }

}

private const val TEST_INPUT = "125 17"