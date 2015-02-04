Minium Pupino - Cucumber Archetype
==================================

Preconditions
--------------

* Ensure [chromedriver](https://code.google.com/p/selenium/wiki/ChromeDriver) is installed and configured.
* Ensure maven is installed and `MAVEN_OPTS` environment variable is set to the following values (or higher):

```
-Xmx768m -XX:MaxPermSize=256m
```

Instructions
------------

Minium cucumber backend is very similar to [Cucumber Rhino](https://github.com/cucumber/cucumber-jvm/tree/master/rhino). Actually, it was adapted from there and even uses the same Javascript API.

You can create a Minium Cucumber test project with the `minium-pupino-cucumber-archetype`. For that, ensure you're in VILT network and then execute the following command in your workspace folder:

```sh
mvn archetype:generate \
  -DarchetypeGroupId=com.vilt-group.minium \
  -DarchetypeArtifactId=minium-pupino-cucumber-archetype \
  -DarchetypeVersion=0.9.7-SNAPSHOT \
  -DarchetypeRepository=https://maven.vilt-group.com/content/repositories/engineering-snapshots/ \
  -DgroupId=my.archetype \
  -DartifactId=my-archetype-test \
  -Dversion=1.0-SNAPSHOT \
  -Dfeature=test_my_archetype \
  -DtestClassname=MyArchetypeIT \
  -DinteractiveMode=false
```

That will create a project `my-archetype-test` with a JUnit structure ready to run, and with Pupino bits for editing. To launch Pupino:

```sh
cd my-archetype-test
mvn verify -P pupino
```

That will take a while, and will open a new browser window (that is controlled with Selenium, don't use it directly), and when it is done, it will open http://localhost:8080/#/editor/ in your browser.

If you just want to run all the tests without pupino:

```sh
cd my-archetype-test
mvn verify
```

You can then add scenarios in `src/test/resources/features/test_my_archetype.feature` and code for new steps in `src/test/resources/steps/test_my_archetype_stepdefs.js`.

# Usage
## 1. Open pupino
Open the browser on this location <http://localhost:8080/>
## 2. Open Test File
* Click on the button **Open File**
* Go to the tab **Navigation**
* Click on **features** and select the file **your-project-name.feature**. 

    At this point you should have the feature file open the editor of pupino.
    
## 3. Run the test
* Click on the button **Run all** to run all the scenarios on the feature file 

### or 

* Put the cursor on one of the scenario line and press **Ctlr** + **enter** (this will execute only the scenario where the cursor is, if you don't want to execute the all feature)

## 4. Execution
In the webdriver opened by pupino you can see the execution of the test. While the tests are being executed the steps of your feature file will be colored giving the result of each step.