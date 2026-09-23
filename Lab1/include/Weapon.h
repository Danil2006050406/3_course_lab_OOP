#ifndef WEAPON_H
#define WEAPON_H

#include "Equipment.h"

class Weapon : public Equipment {
private:
    int damage;

public:
    Weapon(
        const std::string& name,
        double weight,
        double price,
        int damage
    );

    int getDamage() const;

    std::string getType() const override;
};

#endif