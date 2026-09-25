#include "weapons/Sword.h"

Sword::Sword(
    const std::string& name,
    double weight,
    double price,
    int damage
) : Weapon(name, weight, price, damage) {
}

std::string Sword::getType() const {
    return "Sword";
}