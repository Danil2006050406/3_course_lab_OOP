cmake_minimum_required(VERSION 3.20)

project(KnightEquipment)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

include_directories(include)

add_library(KnightLibrary
    src/Equipment.cpp
    src/Weapon.cpp
    src/Armor.cpp
    src/Accessory.cpp
    src/Knight.cpp
    src/weapons/Sword.cpp
    src/weapons/Axe.cpp
    src/weapons/Bow.cpp
)

add_executable(KnightEquipment
    src/main.cpp
)

target_link_libraries(KnightEquipment
    PRIVATE KnightLibrary
)

enable_testing()

find_package(GTest CONFIG REQUIRED)

add_executable(KnightTests
    tests/EquipmentTest.cpp
    tests/KnightTest.cpp
)

target_link_libraries(KnightTests
    PRIVATE
    KnightLibrary
    GTest::gtest
    GTest::gtest_main
)

include(GoogleTest)

gtest_discover_tests(KnightTests)