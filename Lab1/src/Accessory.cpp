#include "Accessory.h"

Accessory::Accessory(
    const std::string& name,
    double weight,
    double price
)
    : Equipment(name, weight, price) {
}

std::string Accessory::getType() const {
    return "Accessory";
}