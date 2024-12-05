object Day05 {

    fun part1() {
        val input = FileReader().readFile("input05.txt")
        val (lists, ruleMap) = parseInput(input)

        var result = 0
        lists.forEach { list ->
            if (isValidList(list, ruleMap)) {
                result += list.midpoint()
            }
        }

        println("day 5, part 1: $result")
    }

    fun part2() {
        val input = FileReader().readFile("input05.txt")
        val (lists, ruleMap) = parseInput(input)

        var result = 0
        lists.forEach { list ->
            if (!isValidList(list, ruleMap)) {
                val reorderedList = reorderList(list, ruleMap)
                result += reorderedList.midpoint()
            }
        }

        println("day 5, part 2: $result")
    }

    private fun parseInput(input: String): Pair<List<List<Int>>, Map<Int, Set<Int>>> {
        val sections = input.split("\n\n")
        val orderedPairs = sections[0].split("\n").filter { it.isNotBlank() }
            .map { it.trim().split("|").map { n -> n.toInt() } }
        val map = orderedPairs.associate<List<Int>, Int, MutableSet<Int>> { it[0] to mutableSetOf() }
        orderedPairs.forEach { e ->
            map[e[0]]!!.add(e[1])
        }

        val lists = sections[1].split("\n").filter { it.isNotBlank() }
            .map { it.trim().split(",").map { n -> n.toInt() } }

        return lists to map
    }

    private fun isValidList(
        list: List<Int>,
        rules: Map<Int, Set<Int>>
    ): Boolean {
        (0..<list.size-1).forEach { i ->
            ((i+1)..<list.size).forEach { j ->
                if (rules[list[j]] != null && rules[list[j]]!!.contains(list[i])) {
                    return false
                }
            }
        }
        return true
    }

    private fun reorderList(
        list: List<Int>,
        rules: Map<Int, Set<Int>>
    ): List<Int> {
        val m = list.toMutableList()
        var isValid = false
        while(!isValid) {
            isValid = true
            (0..<m.size-1).forEach { i ->
                ((i+1)..<m.size).forEach { j ->
                    if (rules[m[j]] != null && rules[m[j]]!!.contains(m[i])) {
                        isValid = false
                        val tmp = m[i]
                        m[i] = m[j]
                        m[j] = tmp
                    }
                }
            }
        }
        return m
    }

    private fun List<Int>.midpoint(): Int {
        if (size % 2 != 1) throw IllegalStateException()
        return this[size / 2]
    }

}

private const val TEST_INPUT = """
47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47
"""