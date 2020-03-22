# Specify the minimum version for CMake
cmake_minimum_required (VERSION 2.6)

# Project's name
project (afd)

# Folder containing all the header (*.hpp) files
include_directories ("src")

# Manually add the sources (*.cpp) using the set command as follows
set (SOURCES src/main.cpp src/DFA.cpp src/Automaton.cpp src/Token.cpp)

# Set compiler options
set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -g -std=c++1z -Wall -O3")

add_executable (afd ${SOURCES})
