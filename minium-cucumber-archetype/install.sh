wget https://chromedriver.storage.googleapis.com/`curl https://chromedriver.storage.googleapis.com/LATEST_RELEASE`/chromedriver_linux`getconf LONG_BIT`.zip -O /tmp/temp.zip
sudo unzip -o -d /usr/bin /tmp/temp.zip
rm /tmp/temp.zip
sudo chmod +x /usr/bin/chromedriver


ARTIFACT=${1:-"my-archetype-test"}


mvn archetype:generate \
  -DarchetypeGroupId=com.vilt-group.minium \
  -DarchetypeArtifactId=minium-pupino-cucumber-archetype \
  -DarchetypeVersion=0.9.6-SNAPSHOT \
  -DarchetypeRepository=https://maven.vilt-group.com/content/repositories/engineering-snapshots/ \
  -DgroupId=my.archetype \
  -DartifactId=$ARTIFACT \
  -Dversion=1.0-SNAPSHOT \
  -Dfeature=test_my_archetype \
  -DtestClassname=MyArchetypeTest \
  -DinteractiveMode=false

cd $ARTIFACT
mvn verify -P pupino

#curl -sSL https://artemis.vilt-group.com/engineering/minium-pupino/raw/master/minium-pupino-cucumber-archetype/install.sh | sh /dev/stdin pupino-test
