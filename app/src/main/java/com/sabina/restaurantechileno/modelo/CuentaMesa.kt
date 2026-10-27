package com.sabina.restaurantechileno.modelo

class CuentaMesa(
    val mesa: Int = 1
) {
    private val items = mutableListOf<ItemMesa>()

    var aceptarPropina: Boolean = true

    fun agregarItem(
        itemMenu: ItemMenu,
        cantidad: Int
    ) {
        require(cantidad >= 0) {
            "La cantidad no puede ser negativa"
        }

        val itemExistente = items.find {
            it.itemMenu.nombre == itemMenu.nombre
        }

        if (itemExistente != null) {
            itemExistente.cantidad = cantidad
        } else {
            items.add(ItemMesa(itemMenu, cantidad))
        }
    }

    fun calcularTotalSinPropina(): Int {
        return items.sumOf { it.calcularSubtotal() }
    }

    fun calcularPropina(): Int {
        return if (aceptarPropina) {
            calcularTotalSinPropina() * 10 / 100
        } else {
            0
        }
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}