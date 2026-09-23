#include "Weapon.h"

Weapon::Weapon(
    const std::string& name,
    double weight,
    double price,
    int damage
)
    : Equipment(name, weight, price),
    damage(damage) {
}

int Weapon::getDamage() const {
    return damage;
}

std::string Weapon::getType() const {
    return "Weapon";
}