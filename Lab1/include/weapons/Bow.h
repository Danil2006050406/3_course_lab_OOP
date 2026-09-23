#ifndef BOW_H
#define BOW_H

#include "Weapon.h"

class Bow : public Weapon {
public:
    Bow(double weight, double price, int damage);

    std::string getType() const override;
};

#endif