package com.coredocker.android.util

import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.FieldPredicates
import org.jeasy.random.api.Randomizer
import java.util.Random

object Randomizer {
    val instance: EasyRandom by lazy { buildRandomize() }

    private fun buildRandomize(): EasyRandom {
        val parameters: EasyRandomParameters = EasyRandomParameters()
        parameters.randomize(
            FieldPredicates.named("name").and(FieldPredicates.ofType(String::class.java)),
            NameRandomizer()
        )
        parameters.randomize(
            FieldPredicates.named("email").and(FieldPredicates.ofType(String::class.java)),
            EmailRandomizer()
        )

        return EasyRandom(parameters)
    }
}

class EmailRandomizer : Randomizer<String?> {
    private val names: NameRandomizer by lazy { NameRandomizer() }
    private val random = Random()

    override fun getRandomValue(): String? {
        return "${names.randomValue}.${random.nextInt(99000)}@emailsmemail.com".replace(" ", ".")
            .toLowerCase()
    }
}

class NameRandomizer : Randomizer<String?> {
    private val names: List<String> =
        listOf(
            "John",
            "William",
            "James",
            "Charles",
            "George",
            "Frank",
            "Joseph",
            "Thomas",
            "Henry",
            "Robert",
            "Edward",
            "Harry",
            "Walter",
            "Arthur",
            "Fred",
            "Albert",
            "Samuel",
            "David",
            "Louis",
            "Joe",
            "Charlie",
            "Clarence",
            "Richard",
            "Andrew",
            "Daniel",
            "Ernest",
            "Will",
            "Jesse",
            "Oscar",
            "Lewis",
            "Peter",
            "Benjamin",
            "Frederick",
            "Willie",
            "Alfred",
            "Sam",
            "Roy",
            "Herbert",
            "Jacob",
            "Tom",
            "Elmer",
            "Carl",
            "Lee",
            "Howard",
            "Martin",
            "Michael",
            "Bert",
            "Herman",
            "Jim",
            "Francis",
            "Harvey",
            "Earl",
            "Eugene",
            "Ralph",
            "Ed",
            "Claude",
            "Edwin",
            "Ben",
            "Charley",
            "Paul",
            "Edgar",
            "Isaac",
            "Otto",
            "Luther",
            "Lawrence",
            "Ira",
            "Patrick",
            "Guy",
            "Oliver",
            "Theodore",
            "Hugh",
            "Clyde",
            "Alexander",
            "August",
            "Floyd",
            "Homer",
            "Jack"
        )

    private val random = Random()

    override fun getRandomValue(): String? {
        return "${names[random.nextInt(names.size - 1)]} ${names[random.nextInt(names.size - 1)]}"
    }
}