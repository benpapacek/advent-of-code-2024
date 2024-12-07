object Day06 {

    fun part1() {
        val input = FileReader().readFile("input06.txt")
        val grid = parseGrid(input)
        val maze = Maze(grid)
        maze.solve()
        val result = maze.getTraversedSquares().size
        println("day 6, part 1: $result")
    }

    fun part2() {
        val input = FileReader().readFile("input06.txt")
        val grid = parseGrid(input)
        val maze = Maze(grid)
        maze.solve()
        var result = 0
        val candidates = maze.getTraversedSquares().filterNot { it == maze.startPos }
        candidates.forEach { pos ->
            maze.clear()
            maze.placeObstacle(pos)
            if (maze.solve() == MazeState.CIRCUIT) {
                result++
            }
        }

        println("day 6, part 2: $result")
    }

    private fun parseGrid(input: String) = input.split("\n").filter { it.isNotBlank() }
        .map { line -> line.toMutableList() }

    private data class Pos(val i: Int, val j: Int)

    private data class Dir(val i: Int, val j: Int, val char: Char)

    private val dirs = listOf(
        Dir(-1, 0, '^'),
        Dir(0, 1, '>'),
        Dir(1, 0, 'v'),
        Dir(0, -1, '<')
    )

    private enum class MazeState {
        STEP, TURN, EXIT, CIRCUIT
    }

    private class Maze(
        val grid: List<MutableList<Char>>
    ) {

        val startPos: Pos
        val startDir: Dir

        var pos: Pos
        var dir: Dir

        init {
            val dirChars = dirs.map { d -> d.char }
            val startPair = grid.mapIndexed { i, line ->
                line.mapIndexed { j, c -> Pos(i, j) to c }
            }.flatten().first { (_, c) -> dirChars.contains(c) }

            startPos = startPair.first
            startDir = dirs.first { it.char == startPair.second }
            pos = startPos
            dir = startDir
        }

        fun move(): MazeState {
            val next = Pos((pos.i + dir.i), (pos.j + dir.j))

            return if (next.i < 0 || next.j < 0 ||
                next.i > grid.size - 1 || next.j > grid[next.i].size - 1
            ) {
                MazeState.EXIT
            } else if (listOf('#', 'O').contains(grid[next.i][next.j])) {
                dir = dirs[(dirs.indexOf(dir) + 1) % dirs.size]
                MazeState.TURN
            } else {
                pos = next
                if (grid[pos.i][pos.j] == dir.char) {
                    MazeState.CIRCUIT
                } else {
                    grid[pos.i][pos.j] = dir.char
                    MazeState.STEP
                }
            }
        }

        fun clear() {
            grid.indices.forEach { i ->
                grid[i].indices.forEach { j ->
                    if (grid[i][j] != '#')
                        grid[i][j] = '.'
                }
            }
            grid[startPos.i][startPos.j] = startDir.char
            pos = startPos
            dir = startDir
        }

        fun solve(): MazeState {
            var mazeState: MazeState
            do {
                mazeState = move()
            } while (mazeState != MazeState.CIRCUIT && mazeState != MazeState.EXIT)
            return mazeState
        }

        fun getTraversedSquares(): List<Pos> {
            return grid.mapIndexed { i, line ->
                line.mapIndexed { j, c ->
                    if (listOf('#', '.', 'O').contains(c)) null else Pos(i, j)
                }
            }.flatten().filterNotNull()
        }

        fun debug() {
            grid.forEach { line ->
                line.forEach { c ->
                    print(c)
                }
                println()
            }
            println()
        }

        fun placeObstacle(pos: Pos) {
            grid[pos.i][pos.j] = 'O'
        }
    }

}

private const val TEST_INPUT = """
....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...
"""
