#ifndef KNIGHT_H
#define KNIGHT_H

#include <memory>
#include <string>
#include <vector>

#include "Equipment.h"

class Knight {
private:
    std::string name;
    std::vector<std::unique_ptr<Equipment>> equipment;

public:
    explicit Knight(const std::string& name);

    void equip(std::unique_ptr<Equipment> item);

    const std::string& getName() const;

    double calculateTotalWeight() const;
    double calculateTotalPrice() const;

    void sortEquipmentByWeight();

    std::vector<const Equipment*> findByPriceRange(
        double minPrice,
        double maxPrice
    ) const;

    const std::vector<std::unique_ptr<Equipment>>& getEquipment() const;
};

#endif