package com.simplefoodscanner.data

import java.time.LocalDateTime

val fakeFoods = listOf(
    Food(code = "0", name = "name0", brands = "brands0",
        date = LocalDateTime.now(), nutriscore = "A"),
    Food(code = "1", name = "name1", brands = "brands1",
        date = LocalDateTime.now().minusDays(1), nutriscore = "B"),
    Food(code = "2", name = "name2", brands = "brands2",
        date = LocalDateTime.now().minusDays(3), nutriscore = "C"),
    Food(code = "3", name = "name3", brands = "brands3",
        date = LocalDateTime.now().minusMonths(1), nutriscore = "D"),
    Food(code = "4", name = "name4", brands = "brands4",
        date = LocalDateTime.now().minusMonths(3), nutriscore = "E"),
    Food(code = "5", name = "name5", brands = "brands5",
        date = LocalDateTime.now().minusYears(1), nutriscore = "D"),
    Food(code = "6", name = "name6", brands = "brands6",
        date = LocalDateTime.now().minusYears(3), nutriscore = "E"),
)

val fakeQuantitiesForFood0 = listOf(
    Quantity(foodCode = "0", name="name1_0", rank=1, level=0, value=12.3, unit="g"),
    Quantity(foodCode = "0", name="name2_0", rank=2, level=1, value=6.2, unit="g"),
    Quantity(foodCode = "0", name="name3_0", rank=3, level=1, value=2.2, unit="g"),
    Quantity(foodCode = "0", name="name4_0", rank=4, level=0, value=3.2, unit="l"),
    Quantity(foodCode = "0", name="name5_0", rank=5, level=0, value=1.2, unit="kg"),
)

val fakeQuantitiesForFood1 = listOf(
    Quantity(foodCode = "1", name="name1_1", rank=1, level=0, value=2.3, unit="g"),
    Quantity(foodCode = "1", name="name2_1", rank=2, level=1, value=2.3, unit="l"),
    Quantity(foodCode = "1", name="name3_1", rank=3, level=2, value=4.2, unit="kg"),
)