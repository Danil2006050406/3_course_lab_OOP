#include <gtest/gtest.h>

#include <memory>

#include "Knight.h"
#include "weapons/Axe.h"
#include "weapons/Bow.h"
#include "weapons/Sword.h"
#include "Armor.h"

TEST(KnightTest, CanEquipWeapons) {
    Knight knight("Richard");

    knight.equip(
        std::make_unique<Sword>(4.5, 750.0, 80)
    );

    knight.equip(
        std::make_unique<Axe>(6.0, 600.0, 95)
    );

    EXPECT_EQ(knight.getEquipment().size(), 2);
}

TEST(KnightTest, CalculatesTotalWeight) {
    Knight knight("Richard");

    knight.equip(
        std::make_unique<Sword>(4.5, 750.0, 80)
    );

    knight.equip(
        std::make_unique<Bow>(2.5, 450.0, 60)
    );

    EXPECT_DOUBLE_EQ(
        knight.calculateTotalWeight(),
        7.0
    );
}

TEST(KnightTest, CalculatesTotalPrice) {
    Knight knight("Richard");

    knight.equip(
        std::make_unique<Sword>(4.5, 750.0, 80)
    );

    knight.equip(
        std::make_unique<Axe>(6.0, 600.0, 95)
    );

    EXPECT_DOUBLE_EQ(
        knight.calculateTotalPrice(),
        1350.0
    );
}
TEST(KnightTest, SortsEquipmentByWeight) {
    Knight knight("Richard");

    knight.equip(
        std::make_unique<Sword>(4.5, 750.0, 80)
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
        std::make_unique<Bow>(2.5, 450.0, 60)
    );

    knight.sortEquipmentByWeight();

    const auto& equipment = knight.getEquipment();

    ASSERT_EQ(equipment.size(), 3);

    EXPECT_EQ(equipment[0]->getType(), "Bow");
    EXPECT_EQ(equipment[1]->getType(), "Sword");
    EXPECT_EQ(equipment[2]->getType(), "Armor");
}
TEST(KnightTest, FindsEquipmentInPriceRange) {
    Knight knight("Richard");

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

    const auto result =
        knight.findByPriceRange(500.0, 1000.0);

    ASSERT_EQ(result.size(), 2);

    EXPECT_EQ(result[0]->getType(), "Sword");
    EXPECT_EQ(result[1]->getType(), "Axe");
}