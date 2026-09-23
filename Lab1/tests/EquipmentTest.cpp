#include <gtest/gtest.h>

#include "Armor.h"
#include "Accessory.h"

TEST(EquipmentTest, ArmorStoresCorrectValues) {
    Armor armor(
        "Steel Armor",
        18.0,
        1500.0,
        90
    );

    EXPECT_EQ(armor.getName(), "Steel Armor");
    EXPECT_DOUBLE_EQ(armor.getWeight(), 18.0);
    EXPECT_DOUBLE_EQ(armor.getPrice(), 1500.0);
    EXPECT_EQ(armor.getProtection(), 90);
    EXPECT_EQ(armor.getType(), "Armor");
}

TEST(EquipmentTest, AccessoryStoresCorrectValues) {
    Accessory shield(
        "Knight Shield",
        5.0,
        800.0
    );

    EXPECT_EQ(shield.getName(), "Knight Shield");
    EXPECT_DOUBLE_EQ(shield.getWeight(), 5.0);
    EXPECT_DOUBLE_EQ(shield.getPrice(), 800.0);
    EXPECT_EQ(shield.getType(), "Accessory");
}