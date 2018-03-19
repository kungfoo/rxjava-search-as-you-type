#!/bin/bash
set -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

DESCRIBE=`git describe --always --tags --long --dirty |\
	sed -E 's/^v(.*)/\1/' | sed -E 's/-([0-9]+)-g([0-9a-z]{7}$)/+c\1.\2/' |\
	sed -E 's/-([0-9]+)-g([0-9a-z]{7})-dirty$/+c\1.\2.dirty/' | sed -E 's/-dirty$/+dirty/'`

VERSION_FILE=${DIR}/build/version.txt

rm -rf ${DIR}/build
mkdir -p ${DIR}/build

echo "Version: ${DESCRIBE}" > ${VERSION_FILE}

echo "Building server distribution files ${DESCRIBE}..."
cd ../../
./gradlew clean :server:distTar
cp server/build/distributions/*.tar ${DIR}/build/

cd ${DIR}
# build and tag the image, if there is a $TAG, otherwise call it 'latest'
DOCKER_TAG=${TAG:-latest}
IMAGE_NAME_INCLUDING_TAG=reactive-server:$DOCKER_TAG

docker build -t $IMAGE_NAME_INCLUDING_TAG .
