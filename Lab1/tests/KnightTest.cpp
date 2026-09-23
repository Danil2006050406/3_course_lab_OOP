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
TEST(KnightTest, SwordStoresCorrectValues) {
    Sword sword(4.5, 750.0, 80);

    EXPECT_DOUBLE_EQ(sword.getWeight(), 4.5);
    EXPECT_DOUBLE_EQ(sword.getPrice(), 750.0);
    EXPECT_EQ(sword.getDamage(), 80);
    EXPECT_EQ(sword.getType(), "Sword");
}

TEST(KnightTest, AxeStoresCorrectValues) {
    Axe axe(6.0, 600.0, 95);

    EXPECT_DOUBLE_EQ(axe.getWeight(), 6.0);
    EXPECT_DOUBLE_EQ(axe.getPrice(), 600.0);
    EXPECT_EQ(axe.getDamage(), 95);
    EXPECT_EQ(axe.getType(), "Axe");
}

TEST(KnightTest, BowStoresCorrectValues) {
    Bow bow(2.5, 450.0, 60);

    EXPECT_DOUBLE_EQ(bow.getWeight(), 2.5);
    EXPECT_DOUBLE_EQ(bow.getPrice(), 450.0);
    EXPECT_EQ(bow.getDamage(), 60);
    EXPECT_EQ(bow.getType(), "Bow");
}