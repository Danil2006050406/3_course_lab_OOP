#ifndef EQUIPMENT_H
#define EQUIPMENT_H

#include <string>

class Equipment {
private:
    std::string name;
    double weight;
    double price;

public:
    Equipment(
        const std::string& name,
        double weight,
        double price
    );

    virtual ~Equipment() = default;

    std::string getName() const;
    double getWeight() const;
    double getPrice() const;

    virtual std::string getType() const = 0;
};

#endif