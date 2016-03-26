#!/usr/bin/env bash
version="1.0.0"
mvn clean package
mvn install:install-file -Dfile=target/common-util-$version.jar -DgroupId=com.fzb -DartifactId=common-util -Dversion=$version -Dpackaging=jar
mvn install:install-file -Dfile=pom.xml -DgroupId=com.fzb -DartifactId=common-util -Dversion=$version -Dpackaging=pom