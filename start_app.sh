#!/bin/bash
./gradlew build -x test && java -jar ./build/libs/coordination-0.0.1-SNAPSHOT.jar