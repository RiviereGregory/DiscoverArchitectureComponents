package gri.riverjach.discoverarchitecturecomponents

fun calculatePrimeNumbers(lowBound: Int, highBound: Int): List<Int> {
    val primeNumbers = mutableListOf<Int>()
    var low = lowBound

    while (low < highBound) {
        var nonPrime = false

        for (i in 2..low / 2) {
            if (low % i == 0) {
                nonPrime = true
                break
            }
        }

        if (!nonPrime) {
            primeNumbers.add(low)
        }

        ++low
    }
    return primeNumbers
}