#!/bin/bash

java -cp "$HOME/.m2/repository/org/hsqldb/hsqldb/2.2.6/hsqldb-2.2.6.jar" org.hsqldb.Server -database.0 data/testdb -dbname.0 testdb