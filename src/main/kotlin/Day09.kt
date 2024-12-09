object Day09 {

    fun part1() {
        val input = FileReader().readFile("input09.txt")
        val a = parseList(input)

        var i = 0
        var j = a.size - 1
        while (i < j) {
            while(a[i].available > 0 && a[j].ids.size > 0) {
                a[i].ids.add(a[j].ids.removeLast())
                a[j].available++
                a[i].available--
            }
            if (a[i].available == 0) i++
            if (a[j].ids.isEmpty()) j--
        }

        val result = calculateChecksum(a)
        println("day 9, part 1: $result")
    }

    fun part2() {
        val input = FileReader().readFile("input09.txt")
        val a = parseList(input)

        var j = a.size - 1
        while (j > 0) {
            var i = 0
            while (i < j && a[i].available < a[j].ids.size)
                i++
            if (i < j) {
                a[i].available -= a[j].ids.size
                a[j].available += a[j].ids.size
                a[i].ids.addAll(a[j].ids)
                a[j].ids.clear()
            }
            j--
        }

        val result = calculateChecksum(a)
        println("day 9, part 2: $result")
    }

    private data class Sector(
        var available: Int,
        val ids: MutableList<Long>
    )

    private fun parseList(input: String): List<Sector> =
        input.trim().mapIndexed { index, c ->
            if (index % 2 == 0) Sector(0, MutableList(c.digitToInt()) { (index / 2).toLong() })
            else Sector(c.digitToInt(), mutableListOf())
        }

    private fun calculateChecksum(a: List<Sector>): Long {
        var index = 0
        var checksum = 0L
        a.forEach { s ->
            s.ids.forEach { id ->
                checksum += index * id
                index++
            }
            index += s.available
        }
        return checksum
    }

    private fun debug(a: List<Sector>) {
        val result = a.joinToString("") {
            it.ids.joinToString("") { c -> c.toString() } + ".".repeat(it.available)
        }
        println(result)
    }

}

private const val TEST_INPUT = "2333133121414131402"