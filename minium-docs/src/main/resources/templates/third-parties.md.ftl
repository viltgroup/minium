# Third-Party Dependencies and Licenses

Here you can see all third party libraries used by Minium and their licenses.

Group ID           | Artifact ID           | Version            | Name                              | License
------------------ | --------------------- | ------------------ | --------------------------------- | -------------------
<#list artifactLicenses as license>
${license.groupId} | ${license.artifactId} | ${license.version} | [${license.name}](${license.url}) | ${license.licenses}
</#list>

Cucumber Rhino is being used in Minium Cucumber, however with some adaptations.