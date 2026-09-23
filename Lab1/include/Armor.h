#ifndef ARMOR_H
#define ARMOR_H

#include "Equipment.h"

class Armor : public Equipment {
private:
    int protection;

public:
    Armor(
        const std::string& name,
        double weight,
        double price,
        int protection
    );

    int getProtection() const;

    std::string getType() const override;
};

#endif