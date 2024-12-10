object Day10 {

    private data class Pos(val i: Int, val j: Int)

    fun part1() {
        val input = FileReader().readFile("input10.txt")
        val grid = input.trim().split("\n").map { it.toList().map { c -> c.digitToInt() } }

        val trailHeads = findTrailHeads(grid)
        val result = trailHeads.sumOf { th ->
            val paths = mutableSetOf<Pos>()
            recursePart1(grid, th, paths)
            paths.size
        }
        println("day 10, part 1: $result")
    }

    fun part2() {
        val input = FileReader().readFile("input10.txt")
        val grid = parseGrid(input)
        val trailHeads = findTrailHeads(grid)
        val result = trailHeads.sumOf { th ->
            val paths = mutableSetOf<List<Pos>>()
            recursePart2(grid, listOf(th), paths)
            paths.size
        }

        println("day 10, part 2: $result")
    }

    private fun parseGrid(input: String) = input.trim().split("\n").map { it.toList().map { c -> c.digitToInt() } }

    private fun findTrailHeads(grid: List<List<Int>>) = grid.indices.map { i ->
        grid[i].indices.map { j ->
            if (grid[i][j] == 0) Pos(i, j) else null
        }
    }.flatten().filterNotNull()

    private fun recursePart1(
        grid: List<List<Int>>,
        pos: Pos,
        resultSet: MutableSet<Pos>,
    ) {
        if (grid[pos.i][pos.j] == 9) {
            resultSet.add(pos)
        } else {
            val candidates = createCandidates(pos, grid)
            val currentValue = grid[pos.i][pos.j]
            candidates.forEach { c ->
                if (grid[c.i][c.j] == currentValue + 1) {
                    recursePart1(grid, c, resultSet)
                }
            }
        }
    }

    private fun recursePart2(
        grid: List<List<Int>>,
        path: List<Pos>,
        resultSet: MutableSet<List<Pos>>,
    ) {
        val pos = path.last()
        if (grid[pos.i][pos.j] == 9) {
            resultSet.add(path)
        } else {
            val candidates = createCandidates(pos, grid)
            val currentValue = grid[pos.i][pos.j]
            candidates.forEach { c ->
                if (grid[c.i][c.j] == currentValue + 1) {
                    recursePart2(grid, path + listOf(c), resultSet)
                }
            }
        }
    }

    private fun createCandidates(pos: Pos, grid: List<List<Int>>) = listOf(
        Pos(pos.i - 1, pos.j), // north
        Pos(pos.i + 1, pos.j), // south
        Pos(pos.i, pos.j - 1), // west
        Pos(pos.i, pos.j + 1)  // east
    ).filter { it.i >= 0 && it.i < grid.size && it.j >= 0 && it.j < grid.size }
}

private const val TEST_INPUT = """
89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732
"""