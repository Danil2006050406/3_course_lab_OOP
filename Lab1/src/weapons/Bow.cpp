#include "weapons/Bow.h"

Bow::Bow(
    const std::string& name,
    double weight,
    double price,
    int damage
) : Weapon(name, weight, price, damage) {
}

std::string Bow::getType() const {
    return "Bow";
}