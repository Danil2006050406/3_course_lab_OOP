#include "Equipment.h"

Equipment::Equipment(
    const std::string& name,
    double weight,
    double price
)
    : name(name),
    weight(weight),
    price(price) {
}

std::string Equipment::getName() const {
    return name;
}

double Equipment::getWeight() const {
    return weight;
}

double Equipment::getPrice() const {
    return price;
}