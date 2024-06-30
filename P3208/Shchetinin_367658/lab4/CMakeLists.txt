cmake_minimum_required(VERSION 3.22)
project(lab4)

set(CMAKE_CXX_STANDARD 20)

include_directories(script)

add_executable(lab4 main.cpp
        Matrix.cpp)
