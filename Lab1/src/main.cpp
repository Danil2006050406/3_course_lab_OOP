#include <iostream>
#include <memory>

#include "Knight.h"
#include "Armor.h"
#include "Accessory.h"
#include "weapons/Axe.h"
#include "weapons/Bow.h"
#include "weapons/Sword.h"

void printKnightInfo(Knight& knight) {
    std::cout << "\n========================================"
        << std::endl;

    std::cout << "Knight: "
        << knight.getName()
        << std::endl;

    std::cout << "========================================"
        << std::endl;

    std::cout << "\nEquipment:"
        << std::endl;

    for (const auto& item : knight.getEquipment()) {
        std::cout << item->getType()
            << ": "
            << item->getName()
            << " | Weight: "
            << item->getWeight()
            << " kg | Price: "
            << item->getPrice()
            << std::endl;
    }

    std::cout << "\nTotal weight: "
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

    if (foundEquipment.empty()) {
        std::cout << "No equipment found."
            << std::endl;
    }

    for (const auto* item : foundEquipment) {
        std::cout << item->getType()
            << ": "
            << item->getName()
            << " - "
            << item->getPrice()
            << std::endl;
    }
}

int main() {
    Knight richard("Richard the Lionheart");
    Knight anton("Sirovar Antoun");
    Knight sashko("Sir Fafhko");

    richard.equip(
        std::make_unique<Sword>(
            "Excalibur",
            3.2,
            5000.0,
            150
        )
    );

    richard.equip(
        std::make_unique<Axe>(
            "Battle Axe",
            5.5,
            2500.0,
            130
        )
    );

    richard.equip(
        std::make_unique<Armor>(
            "Steel Armor",
            18.0,
            1500.0,
            90
        )
    );

    richard.equip(
        std::make_unique<Accessory>(
            "Knight Shield",
            5.0,
            800.0
        )
    );

    anton.equip(
        std::make_unique<Sword>(
            "Dildosword",
            4.5,
            3000.0,
            300
        )
    );

    anton.equip(
        std::make_unique<Bow>(
            "Long Bow",
            2.5,
            1800.0,
            90
        )
    );

    anton.equip(
        std::make_unique<Armor>(
            "Leather Armor",
            8.0,
            1200.0,
            50
        )
    );

    anton.equip(
        std::make_unique<Accessory>(
            "Fisting Ring",
            0.1,
            2500.0
        )
    );

    sashko.equip(
        std::make_unique<Sword>(
            "Silver Blade",
            2.8,
            2200.0,
            100
        )
    );

    sashko.equip(
        std::make_unique<Axe>(
            "Mjolnir",
            6.0,
            7000.0,
            200
        )
    );

    sashko.equip(
        std::make_unique<Bow>(
            "Hunter Bow",
            2.0,
            2200.0,
            100
        )
    );

    sashko.equip(
        std::make_unique<Armor>(
            "Berserk Armor",
            12.0,
            2800.0,
            70
        )
    );

    sashko.equip(
        std::make_unique<Accessory>(
            "Viking Amulet",
            0.3,
            1500.0
        )
    );

    printKnightInfo(richard);
    printKnightInfo(anton);
    printKnightInfo(sashko);

    return 0;
}