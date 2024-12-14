object Day14 {

    private data class Robot(
        val startX: Int,
        val startY: Int,
        val dx: Int,
        val dy: Int,
    ) {
        var x = startX
        var y = startY
    }

    private data class Bounds(val w: Int, val h: Int)

    fun part1() {
        val input = FileReader().readFile("input14.txt")
        val bounds = Bounds(101, 103)
        val robots = parseRobots(input)

        repeat(100) {
            robots.forEach { robot ->
                robot.x = Math.floorMod(robot.x + robot.dx, bounds.w)
                robot.y = Math.floorMod(robot.y + robot.dy, bounds.h)
            }
        }
        val safetyFactor = calculateSafetyFactor(robots, bounds)

        println("day 14, part 1: $safetyFactor")
    }

    fun part2() {
        val input = FileReader().readFile("input14.txt")
        val bounds = Bounds(101, 103)
        val robots = parseRobots(input)

        val a = Array(bounds.h) { Array(bounds.w) { '.' } }
        var i = 1
        while(true) {
            robots.forEach { robot ->
                robot.x = Math.floorMod(robot.x + robot.dx, bounds.w)
                robot.y = Math.floorMod(robot.y + robot.dy, bounds.h)
            }
            plot(robots, a)
            val neighbours = countNeighbours(robots, a)
            if (neighbours > robots.size * 0.6) { // 0.6 found by experimentation
                break
            }
            i++
        }
        println("day 14, part 2: $i")
    }

    private fun parseRobots(input: String) = input.trim().split("\n").map { line ->
        Regex("p=(\\d+),(\\d+) v=([\\d-]+),([\\d-]+)").matchEntire(line)!!.groupValues.let { g ->
            Robot(g[1].toInt(), g[2].toInt(), g[3].toInt(), g[4].toInt())
        }
    }

    private fun calculateSafetyFactor(robots: List<Robot>, bounds: Bounds): Int {
        val (xMid, yMid) = bounds.w/2 to bounds.h/2
        val q1 = robots.count { r -> r.x < xMid && r.y < yMid }
        val q2 = robots.count { r -> r.x > xMid && r.y < yMid }
        val q3 = robots.count { r -> r.x < xMid && r.y > yMid }
        val q4 = robots.count { r -> r.x > xMid && r.y > yMid }
        return q1 * q2 * q3 * q4
    }

    private fun countNeighbours(
        robots: List<Robot>,
        a: Array<Array<Char>>
    ): Int {
        var total = 0
        robots.forEach { r ->
            val neighbours = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)
            if (neighbours.any {
                val (x, y) = it
                runCatching { a[r.y + y][r.x + x] != '.' }.getOrDefault(false)
            }) {
                total++
            }
        }
        return total
    }

    private fun plot(robots: List<Robot>, a: Array<Array<Char>>) {
        a.indices.forEach { i ->
            a[i].indices.forEach { j ->
                a[i][j] = '.'
            }
        }
        robots.forEach {
            a[it.y][it.x] = if (a[it.y][it.x] == '.') '1' else (a[it.y][it.x].digitToInt() + 1).toString()[0]
        }
    }

    private fun debug(a: Array<Array<Char>>) {
        (a.indices).forEach { y ->
            a[y].indices.forEach { x ->
                print(a[y][x])
            }
            println()
        }
        println()
    }
}

private const val TEST_INPUT = """
p=0,4 v=3,-3
p=6,3 v=-1,-3
p=10,3 v=-1,2
p=2,0 v=2,-1
p=0,0 v=1,3
p=3,0 v=-2,-2
p=7,6 v=-1,-3
p=3,0 v=-1,-2
p=9,3 v=2,3
p=7,3 v=-1,2
p=2,4 v=2,-3
p=9,5 v=-3,-3
"""
