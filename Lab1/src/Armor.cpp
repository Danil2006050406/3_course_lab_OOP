#include "Armor.h"

Armor::Armor(
    const std::string& name,
    double weight,
    double price,
    int protection
)
    : Equipment(name, weight, price),
    protection(protection) {
}

int Armor::getProtection() const {
    return protection;
}

std::string Armor::getType() const {
    return "Armor";
}