object Day08 {

    data class Square(val i: Int, val j: Int, val char: Char?, var isAntinode: Boolean = false)
    private val Pair<Int, Int>.i get() = this.first
    private val Pair<Int, Int>.j get() = this.second
    private fun Pair<Int, Int>.withinBounds(grid: List<List<Any>>): Boolean =
        (i >= 0 && j >= 0 && i < grid.size && j < grid[i].size)

    fun part1() {
        val input = FileReader().readFile("input08.txt")
        val grid = parseGrid(input)
        val groups = parseGroups(grid)

        groups.forEach { group ->
            for (x in group.indices) {
                for (y in group.indices) {
                    if (x == y) continue
                    val delta = group[y].i - group[x].i to group[y].j - group[x].j
                    val antinode = group[x].i + delta.i * 2 to group[x].j + delta.j * 2
                    if (antinode.withinBounds(grid)) {
                        grid[antinode.i][antinode.j].isAntinode = true
                    }
                }
            }
        }

        val result = grid.sumOf { line -> line.count { it.isAntinode } }
        println("day 8, part 1: $result")
    }

    fun part2() {
        val input = FileReader().readFile("input08.txt")
        val grid = parseGrid(input)
        val groups = parseGroups(grid)

        groups.forEach { group ->
            for (x in group.indices) {
                group[x].isAntinode = true
                for (y in group.indices) {
                    if (x == y) continue
                    val delta = group[y].i - group[x].i to group[y].j - group[x].j
                    var antinode = group[x].i + delta.i * 2 to group[x].j + delta.j * 2
                    while (antinode.withinBounds(grid)) {
                        grid[antinode.i][antinode.j].isAntinode = true
                        antinode = antinode.i + delta.i to antinode.j + delta.j
                    }
                }
            }
        }

        val result = grid.sumOf { line -> line.count { it.isAntinode } }
        println("day 8, part 2: $result")
    }

    private fun parseGrid(input: String): List<List<Square>> =
        input.trim().split("\n")
            .mapIndexed { i, line ->
                line.mapIndexed { j, c ->
                    val actualChar = if (c == '.') null else c
                    Square(i, j, actualChar)
                }
            }

    private fun parseGroups(grid: List<List<Square>>) =
        grid.flatten().filter { it.char != null }.groupBy { it.char }.values.toList()

    private fun debug(grid: List<List<Square>>) {
        grid.forEach { line ->
            line.forEach { square ->
                if (square.char != null) print(square.char)
                else if (square.isAntinode) print('#')
                else print('.')
            }
            println()
        }
    }
}

private const val TEST_INPUT = """
............
........0...
.....0......
.......0....
....0.......
......A.....
............
............
........A...
.........A..
............
............
"""