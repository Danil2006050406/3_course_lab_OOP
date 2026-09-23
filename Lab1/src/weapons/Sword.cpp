#include "weapons/Sword.h"

Sword::Sword(double weight, double price, int damage)
    : Weapon("Long Sword", weight, price, damage) {
}

std::string Sword::getType() const {
    return "Sword";
}