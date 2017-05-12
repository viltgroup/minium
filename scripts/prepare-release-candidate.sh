#!/bin/sh
set -e

### IMPORTANT: this script is to use only in Gitlab CI!

# computes final release version
FINAL_VERSION=$(echo $CI_COMMIT_REF_NAME | cut -d '/' -f 2)

# computes current version
DEV_VERSION=$(mvn exec:exec -q --non-recursive -Dexec.executable="echo" -Dexec.args='${project.version}')

# counts how many RC versions there are for this specific version
RC_VERSIONS_COUNT=$(git tag -l $CI_PROJECT_NAME-$FINAL_VERSION-RC* | wc -l)

# increment RC_VERSIONS_COUNT to get the next release candidate
RELEASE_VERSION=$(echo $CI_COMMIT_REF_NAME | cut -d '/' -f 2)-RC$((RC_VERSIONS_COUNT + 1))

# git tag name to use
TAG=$CI_PROJECT_NAME-$RELEASE_VERSION

echo "Preparing release candidate $RELEASE_VERSION and keeping development cycle back to $DEV_VERSION"
chronic mvn --batch-mode release:prepare release:perform -Dtag=$TAG -DreleaseVersion=$RELEASE_VERSION -DdevelopmentVersion=$DEV_VERSION $MAVEN_EXTRA_ARGS $MAVEN_RELEASE_EXTRA_ARGS
