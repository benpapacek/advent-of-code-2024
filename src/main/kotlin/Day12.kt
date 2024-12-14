object Day12 {

    private data class Square(
        val i: Int,
        val j: Int,
        val char: Char,
    )

    fun part1() {
        val input = FileReader().readFile("input12.txt")
        val a = parseInput(input)
        val groups = createGroups(a)

        val result = groups.sumOf { group ->
            group.size * group.sumOf { findPerimeter(a, it) }
        }

        println("day 12, part 1: $result")
    }

    fun part2() {
        val input = FileReader().readFile("input12.txt")
        val a = parseInput(input)
        val groups = createGroups(a)
        val result2 = groups.sumOf { group ->
            group.size * findEdgeCount(a, group)
        }

        println("day 12, part 2: $result2")
    }

    private fun parseInput(input: String): List<List<Square>> =
        input.trim().split("\n").mapIndexed { i, row ->
            row.mapIndexed { j, c -> Square(i, j, c) }
        }

    private fun createGroups(a: List<List<Square>>): List<Set<Square>> {
        val groups: MutableList<Set<Square>> = mutableListOf()
        a.forEach { row ->
            row.forEach { square ->
                if (!groups.any { it.contains(square) }) {
                    val group = mutableSetOf<Square>()
                    createGroupRecursive(a, group, square)
                    groups.add(group)
                }
            }
        }
        return groups
    }

    private fun createGroupRecursive(
        a: List<List<Square>>,
        group: MutableSet<Square>,
        s: Square,
    ) {
        if (group.contains(s))
            return
        group.add(s)

        val (i, j) = s.i to s.j
        val neighbours = listOfNotNull(
            runCatching { a[i-1][j] }.getOrNull(),
            runCatching { a[i+1][j] }.getOrNull(),
            runCatching { a[i][j-1] }.getOrNull(),
            runCatching { a[i][j+1] }.getOrNull(),
        )
        neighbours.forEach { n ->
            if (n.char == s.char) {
                createGroupRecursive(a, group, n)
            }
        }
    }

    private fun findEdgeCount(
        a: List<List<Square>>,
        group: Set<Square>
    ): Int {
        val northEdges: MutableSet<Set<Square>> = mutableSetOf()
        val southEdges: MutableSet<Set<Square>> = mutableSetOf()
        val eastEdges: MutableSet<Set<Square>> = mutableSetOf()
        val westEdges: MutableSet<Set<Square>> = mutableSetOf()

        group.forEach { s ->
            if (isNorthEdge(a, s)) {
                val edge = mutableSetOf<Square>()
                var (i, j) = s.i to s.j
                while (j >= 0 && a[i][j].char == s.char && isNorthEdge(a, a[i][j])) {
                    edge.add(a[s.i][j])
                    j--
                }
                j = s.j
                while (j < a[i].size && a[i][j].char == s.char && isNorthEdge(a, a[i][j])) {
                    edge.add(a[i][j])
                    j++
                }
                northEdges.add(edge)
            }
            if (isSouthEdge(a, s)) {
                val edge = mutableSetOf<Square>()
                var (i, j) = s.i to s.j
                while (j >= 0 && a[i][j].char == s.char && isSouthEdge(a, a[i][j])) {
                    edge.add(a[i][j])
                    j--
                }
                j = s.j
                while (j < a[i].size && a[i][j].char == s.char && isSouthEdge(a, a[i][j])) {
                    edge.add(a[i][j])
                    j++
                }
                southEdges.add(edge)
            }
            if (isEastEdge(a, s)) {
                val edge = mutableSetOf<Square>()
                var (i, j) = s.i to s.j
                while (i >= 0 && a[i][j].char == s.char && isEastEdge(a, a[i][j])) {
                    edge.add(a[i][j])
                    i--
                }
                i = s.i
                while (i < a.size && a[i][j].char == s.char && isEastEdge(a, a[i][j])) {
                    edge.add(a[i][j])
                    i++
                }
                eastEdges.add(edge)
            }
            if (isWestEdge(a, s)) {
                val edge = mutableSetOf<Square>()
                var (i, j) = s.i to s.j
                while (i >= 0 && a[i][j].char == s.char && isWestEdge(a, a[i][j])) {
                    edge.add(a[i][j])
                    i--
                }
                i = s.i
                while (i < a.size && a[i][j].char == s.char && isWestEdge(a, a[i][j])) {
                    edge.add(a[i][j])
                    i++
                }
                westEdges.add(edge)
            }
        }
        return northEdges.size + southEdges.size + eastEdges.size + westEdges.size
    }

    private fun findPerimeter(
        a: List<List<Square>>,
        s: Square
    ) = listOf(
        isNorthEdge(a, s),
        isSouthEdge(a, s),
        isEastEdge(a, s),
        isWestEdge(a, s)
    ).filter { it }.size

    private fun isNorthEdge(a: List<List<Square>>, s: Square) = runCatching { a[s.i-1][s.j].char != s.char }.getOrDefault(true)
    private fun isSouthEdge(a: List<List<Square>>, s: Square) = runCatching { a[s.i+1][s.j].char != s.char }.getOrDefault(true)
    private fun isWestEdge(a: List<List<Square>>, s: Square) = runCatching { a[s.i][s.j-1].char != s.char }.getOrDefault(true)
    private fun isEastEdge(a: List<List<Square>>, s: Square) = runCatching { a[s.i][s.j+1].char != s.char }.getOrDefault(true)
}

private const val TEST_INPUT = """
RRRRIICCFF
RRRRIICCCF
VVRRRCCFFF
VVRCCCJFFF
VVVVCJJCFE
VVIVCCJJEE
VVIIICJJEE
MIIIIIJJEE
MIIISIJEEE
MMMISSJEEE
"""