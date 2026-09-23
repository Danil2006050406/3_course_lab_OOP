#include "weapons/Axe.h"

Axe::Axe(double weight, double price, int damage)
    : Weapon("Battle Axe", weight, price, damage) {
}

std::string Axe::getType() const {
    return "Axe";
}