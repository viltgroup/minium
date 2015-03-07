# Changelog

<#list versions as version>
## ${version}
	<#assign tickets = tickets[version]/>
    <#list tickets as ticket>
- [${ticket.tracker} #${ticket.id}](${ticket.url}): ${ticket.subject}
    </#list>
</#list>
