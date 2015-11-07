@echo off
echo [INFO] Generating project in ../generated-projects
cd ../
if not exist  generated-projects mkdir generated-projects
cd generated-projects
call mvn archetype:generate -DarchetypeGroupId=com.xyxy.platform -DarchetypeArtifactId=platform-quickstart-archetype -DarchetypeVersion=1.0-SNAPSHOT

pause


