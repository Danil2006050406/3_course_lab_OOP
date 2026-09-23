#include "weapons/Bow.h"

Bow::Bow(double weight, double price, int damage)
    : Weapon("Long Bow", weight, price, damage) {
}

std::string Bow::getType() const {
    return "Bow";
}