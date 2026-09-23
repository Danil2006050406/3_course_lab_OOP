#include <iostream>
#include <memory>

#include "Knight.h"
#include "Armor.h"
#include "Accessory.h"
#include "weapons/Axe.h"
#include "weapons/Bow.h"
#include "weapons/Sword.h"

int main() {
    Knight knight("Richard the Lionheart");

    knight.equip(
        std::make_unique<Sword>(4.5, 750.0, 80)
    );

    knight.equip(
        std::make_unique<Axe>(6.0, 600.0, 95)
    );

    knight.equip(
        std::make_unique<Bow>(2.5, 450.0, 60)
    );

    knight.equip(
        std::make_unique<Armor>(
            "Steel Armor",
            18.0,
            1500.0,
            90
        )
    );

    knight.equip(
        std::make_unique<Accessory>(
            "Knight Shield",
            5.0,
            800.0
        )
    );

    std::cout << "Knight: "
        << knight.getName()
        << std::endl;

    std::cout << "Total weight: "
        << knight.calculateTotalWeight()
        << " kg"
        << std::endl;

    std::cout << "Total price: "
        << knight.calculateTotalPrice()
        << std::endl;

    knight.sortEquipmentByWeight();

    std::cout << "\nEquipment sorted by weight:"
        << std::endl;

    for (const auto& item : knight.getEquipment()) {
        std::cout << item->getType()
            << ": "
            << item->getName()
            << " - "
            << item->getWeight()
            << " kg"
            << std::endl;
    }

    const double minPrice = 500.0;
    const double maxPrice = 1000.0;

    const auto foundEquipment =
        knight.findByPriceRange(minPrice, maxPrice);

    std::cout << "\nEquipment with price from "
        << minPrice
        << " to "
        << maxPrice
        << ":"
        << std::endl;

    for (const auto* item : foundEquipment) {
        std::cout << item->getType()
            << ": "
            << item->getName()
            << " - "
            << item->getPrice()
            << std::endl;
    }

    return 0;
}