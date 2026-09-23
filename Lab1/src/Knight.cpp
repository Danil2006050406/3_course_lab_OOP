#include "Knight.h"
#include <algorithm>

Knight::Knight(const std::string& name)
    : name(name) {
}

void Knight::equip(std::unique_ptr<Equipment> item) {
    if (item != nullptr) {
        equipment.push_back(std::move(item));
    }
}

const std::string& Knight::getName() const {
    return name;
}

double Knight::calculateTotalWeight() const {
    double totalWeight = 0.0;

    for (const auto& item : equipment) {
        totalWeight += item->getWeight();
    }

    return totalWeight;
}

double Knight::calculateTotalPrice() const {
    double totalPrice = 0.0;

    for (const auto& item : equipment) {
        totalPrice += item->getPrice();
    }

    return totalPrice;
}
void Knight::sortEquipmentByWeight() {
    std::sort(
        equipment.begin(),
        equipment.end(),
        [](const std::unique_ptr<Equipment>& first,
            const std::unique_ptr<Equipment>& second) {
                return first->getWeight() < second->getWeight();
        }
    );
}
std::vector<const Equipment*> Knight::findByPriceRange(
    double minPrice,
    double maxPrice
) const {
    std::vector<const Equipment*> result;

    for (const auto& item : equipment) {
        if (item->getPrice() >= minPrice &&
            item->getPrice() <= maxPrice) {
            result.push_back(item.get());
        }
    }

    return result;
}

const std::vector<std::unique_ptr<Equipment>>&
Knight::getEquipment() const {
    return equipment;
}