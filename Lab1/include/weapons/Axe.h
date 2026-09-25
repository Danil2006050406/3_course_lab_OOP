#ifndef AXE_H
#define AXE_H

#include "Weapon.h"

class Axe : public Weapon {
public:
    Axe(
        const std::string& name,
        double weight,
        double price,
        int damage
    );
    std::string getType() const override;
};

#endif