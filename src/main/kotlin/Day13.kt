import java.math.BigInteger

object Day13 {

    private data class Button(val dx: Long, val dy: Long)

    private data class Prize(val x: Long, val y: Long)

    private data class Machine(
        val buttonA: Button,
        val buttonB: Button,
        val prize: Prize
    )

    fun part1() {
        val input = FileReader().readFile("input13.txt")
        val machines = parseMachines(input)

        val optima = machines.map { m ->
            findOptimumProgrammatically(m)
        }
        val tokens = optima.filterNotNull().sumOf { it.first * 3L + it.second }

        println("day 13, part 1: $tokens")
    }

    fun part2() {
        val input = FileReader().readFile("input13.txt")
        val machines = parseMachines(input).map { m ->
            m.copy(prize = Prize(m.prize.x + 10000000000000, m.prize.y + 10000000000000))
        }

        val optima = machines.map { m ->
            findOptimumUsingEquation(m)
        }
        val tokens = optima.filterNotNull().sumOf { it.first * 3L + it.second }

        println("day 13, part 2: $tokens")
    }

    private fun parseMachines(input: String) = input.trim().split("\n\n").map { m ->
        val a = m.split("\n")
        val buttonA = Regex("Button A: X\\+(\\d+), Y\\+(\\d+)").find(a[0])!!.groupValues.let { v ->
            Button(v[1].toLong(), v[2].toLong())
        }
        val buttonB = Regex("Button B: X\\+(\\d+), Y\\+(\\d+)").find(a[1])!!.groupValues.let { v ->
            Button(v[1].toLong(), v[2].toLong())
        }
        val prize = Regex("Prize: X=(\\d+), Y=(\\d+)").find(a[2])!!.groupValues.let { v ->
            Prize(v[1].toLong(), v[2].toLong())
        }
        Machine(
            buttonA = buttonA,
            buttonB = buttonB,
            prize = prize
        )
    }

    private fun findOptimumUsingEquation(m: Machine): Pair<Long, Long>? {
        val adx = BigInteger.valueOf(m.buttonA.dx)
        val bdx = BigInteger.valueOf(m.buttonB.dx)
        val ady = BigInteger.valueOf(m.buttonA.dy)
        val bdy = BigInteger.valueOf(m.buttonB.dy)
        val px = BigInteger.valueOf(m.prize.x)
        val py = BigInteger.valueOf(m.prize.y)

        val b = (adx * py - ady * px).divideAndRemainder(adx * bdy - ady * bdx)
        if (b[1] != BigInteger.ZERO) return null
        val a = (px - bdx * b[0]).divideAndRemainder(adx)
        if (a[1] != BigInteger.ZERO) return null

        return a[0].toLong() to b[0].toLong()
    }

    private fun findOptimumProgrammatically(m: Machine): Pair<Long, Long>? {
        val candidates: MutableList<Pair<Long, Long>> = mutableListOf()
        var a = 0
        while (a <= 100 && (a * m.buttonA.dx) < m.prize.x) {
            if ((m.prize.x - a * m.buttonA.dx) % m.buttonB.dx == 0L) {
                val b = (m.prize.x - a * m.buttonA.dx) / m.buttonB.dx
                if (a * m.buttonA.dy + b * m.buttonB.dy == m.prize.y) {
                    candidates.add(a.toLong() to b)
                }
            }
            a++
        }
        return candidates.minByOrNull { it.first }
    }

}


private const val TEST_INPUT = """
Button A: X+94, Y+34
Button B: X+22, Y+67
Prize: X=8400, Y=5400

Button A: X+26, Y+66
Button B: X+67, Y+21
Prize: X=12748, Y=12176

Button A: X+17, Y+86
Button B: X+84, Y+37
Prize: X=7870, Y=6450

Button A: X+69, Y+23
Button B: X+27, Y+71
Prize: X=18641, Y=10279
"""
