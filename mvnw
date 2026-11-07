#!/bin/sh
BASE_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
if [ -z "$JAVA_HOME" ]; then
  echo "JAVA_HOME must point to a JDK installation" >&2
  exit 1
fi
exec "$JAVA_HOME/bin/java" -classpath "$BASE_DIR/.mvn/wrapper/maven-wrapper.jar" "-Dmaven.multiModuleProjectDirectory=$BASE_DIR" org.apache.maven.wrapper.MavenWrapperMain "$@"
