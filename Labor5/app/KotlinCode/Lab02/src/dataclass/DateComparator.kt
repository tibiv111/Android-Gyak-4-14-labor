package dataclass

class DateComparator : Comparator<Date> {

    override fun compare(o1: Date?, o2: Date?): Int {
        if (o1 == null || o2 == null)
        {
            return 0
        }
        val yearcmp = o1.year - o2.year
        if (yearcmp == 0)
        {
            val monthcmp = o1.month - o2.month
            if (monthcmp == 0)
            {
                return o1.day - o2.day

            }
            return monthcmp

        }
        return yearcmp
    }
}