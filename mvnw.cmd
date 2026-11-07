@echo off
setlocal
set "MAVEN_PROJECTBASEDIR=%~dp0."
if not defined JAVA_HOME (
  echo JAVA_HOME must point to a JDK installation 1>&2
  exit /b 1
)
"%JAVA_HOME%\bin\java.exe" -classpath "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar" "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" org.apache.maven.wrapper.MavenWrapperMain %*
exit /b %ERRORLEVEL%

