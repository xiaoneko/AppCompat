cmake_minimum_required(VERSION 3.22.1)

find_library(log-lib log)
find_library(android-lib android)

project(YourProjectName)

set(CMAKE_C_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED True)

set(SOURCE_DIR src/main/cpp)

add_library(cpp SHARED main.cpp)

set_target_properties(cpp PROPERTIES OUTPUT_NAME "cpp")

target_link_libraries(cpp
    ${log}
    ${android}
)

target_link_options(cpp PRIVATE "-Wl,-z,max-page-size=65536")

# 4096 16384 65536