#include "weapons/Axe.h"

Axe::Axe(
    const std::string& name,
    double weight,
    double price,
    int damage
) : Weapon(name, weight, price, damage) {
}

std::string Axe::getType() const {
    return "Axe";
}