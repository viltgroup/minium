# Third-Party Dependencies and Licenses

Here you can see all third party libraries used by Minium and their licenses.

## Maven Artifacts

Group ID           | Artifact ID           | Version            | Name                              | License
------------------ | --------------------- | ------------------ | --------------------------------- | -------------------
<#list artifactLicenses as license>
${license.groupId} | ${license.artifactId} | ${license.version} | [${license.name}](${license.url}) | ${license.licenses}
</#list>

## Javascript libraries

Name                                                   | Version | License
------------------------------------------------------ | ------- | ---------------
[jQuery](http://jquery.com/)                           | 1.11.2  | The MIT License
[elements.js](https://github.com/Automattic/expect.js) | 0.3.1   | The MIT License
