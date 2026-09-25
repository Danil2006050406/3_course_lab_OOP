#ifndef SWORD_H
#define SWORD_H

#include "Weapon.h"

class Sword : public Weapon {
public:
    Sword(
        const std::string& name,
        double weight,
        double price,
        int damage
    );

    std::string getType() const override;
};

#endif