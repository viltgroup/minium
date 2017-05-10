#!/bin/sh
set -e

### IMPORTANT: this script is to use only in Gitlab CI!

# computes final release version and next snapshot version
RELEASE_VERSION=$(echo $CI_COMMIT_REF_NAME | cut -d '/' -f 2)

# git tag name to use
TAG=$CI_PROJECT_NAME-$RELEASE_VERSION

echo "Preparing release $RELEASE_VERSION"
chronic mvn --batch-mode release:prepare release:perform -Dtag=$TAG -DreleaseVersion=$RELEASE_VERSION $MAVEN_EXTRA_ARGS $MAVEN_RELEASE_EXTRA_ARGS
