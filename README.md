# Minium - Quick start

## Preconditions

Ensure that the following software is installed:

* Java JDK / JRE 1.6+ (required)
* Google Chrome (recommended)

## Minium Installation

* Install chrome-driver
* Set PATH=<chrome-driver directory>;%PATH% (windows)
* Set PATH=<chrome-driver directory>:$PATH (*nix)
* Download minium-webconsole-jetty-${project.version}.zip
* Unzip it in some folder
* Launch it:

```bash
cd <minium-webconsole-jetty directory>
java -jar start.jar
```

* Open it on Chrome: http://localhost:8080/minium-webconsole
* Create a shortcut for it (Tools > Create Application...)

Now you can launch the server and open the chrome app.