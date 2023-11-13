package com.simplefoodscanner.data

import com.simplefoodscanner.data.source.local.LocalFood
import com.simplefoodscanner.data.source.local.LocalQuantity
import com.simplefoodscanner.data.source.network.NetworkFood
import java.time.LocalDateTime

fun LocalFood.toExternal() = Food(code, name, brands, nutriscore, date)

@JvmName("foodToExternal")
fun List<LocalFood>.toExternal() = map(LocalFood::toExternal)
fun Food.toLocal() = LocalFood(code, name, brands, nutriscore, date)
@JvmName("foodToLocal")
fun List<Food>.toLocal() = map(Food::toLocal)

fun LocalQuantity.toExternal() = Quantity(foodCode, name, rank, level, value, unit)
@JvmName("quantityToExternal")
fun List<LocalQuantity>.toExternal() = map(LocalQuantity::toExternal)
fun Quantity.toLocal() = LocalQuantity(foodCode, name, rank, level, value, unit)
@JvmName("quantityToLocal")
fun List<Quantity>.toLocal() = map(Quantity::toLocal)

fun NetworkFood.toLocal() = LocalFood(code, name, brands, nutriscore, LocalDateTime.now())

fun NetworkFood.toLocalQuantities() = quantities.map {
        q -> LocalQuantity(code, q.name, q.rank, q.level, q.quantity, q.unit)
}