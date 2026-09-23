#ifndef ACCESSORY_H
#define ACCESSORY_H

#include "Equipment.h"

class Accessory : public Equipment {
public:
    Accessory(
        const std::string& name,
        double weight,
        double price
    );

    std::string getType() const override;
};

#endif