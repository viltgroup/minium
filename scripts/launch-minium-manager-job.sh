#!/bin/sh

### IMPORTANT: this script is to use only in Gitlab CI!

# variables
MINIUM_MANAGER_URL="https://bolina.vilt-group.com"
BROWSERS="[{\"name\":\"chrome\"}]"
CREDENTIALS="$MINIUM_MANAGER_USER:$MINIUM_MANAGER_PASS"

# we'll store cookies here, and with it we'll be able to access non-api URLs
COOKIE_JAR=$(mktemp)

TEST_EXECUTION_NUMBER=$(curl -u "$CREDENTIALS" --cookie-jar $COOKIE_JAR --request POST --form "browsers=$BROWSERS" --form "fields=number" "$MINIUM_MANAGER_URL/api/projects/$MINIUM_MANAGER_PROJECT_ID/test-execution" -s | sed '/number/!d' | grep -Po [0-9]+)
if [ -z "$TEST_EXECUTION_NUMBER" ]; then
  echo "Unable to launch test execution on $MINIUM_MANAGER_URL"
  exit 1
fi

echo "Number of the execution: $TEST_EXECUTION_NUMBER"

while [ "$TEST_EXECUTION_STATE" != "FINISHED" ]
do
  sleep 10
  TEST_EXECUTION_STATE=$(curl -b $COOKIE_JAR "$MINIUM_MANAGER_URL/api/projects/$MINIUM_MANAGER_PROJECT_ID/test-execution/$TEST_EXECUTION_NUMBER?fields=state" -s | sed '/state/!d' | grep -Po [A-Z]+)
  echo "State of the execution: $TEST_EXECUTION_STATE"
done

TEST_EXECUTION_ID=$(curl -b $COOKIE_JAR "$MINIUM_MANAGER_URL/api/projects/$MINIUM_MANAGER_PROJECT_ID/test-execution/$TEST_EXECUTION_NUMBER?fields=id" -s | sed '/id/!d' | grep -Po [0-9]+)
TEST_EXECUTION_RESULT=$(curl -b $COOKIE_JAR "$MINIUM_MANAGER_URL/api/projects/$MINIUM_MANAGER_PROJECT_ID/test-execution/$TEST_EXECUTION_NUMBER?fields=result" -s | sed '/result/!d' | grep -Po [A-Z]+)

curl -b $COOKIE_JAR "$MINIUM_MANAGER_URL/app/rest/builds/$TEST_EXECUTION_ID/report/PDF?with-screenshots=true" -s -o "$MINIUM_REPORT_PATH"

if [ "$TEST_EXECUTION_RESULT" != "SUCCESS" ]
then
  echo "Execution didn't succeded, result was $TEST_EXECUTION_RESULT"
  exit 1
else
  echo "Execution succeded!"
  exit 0
fi