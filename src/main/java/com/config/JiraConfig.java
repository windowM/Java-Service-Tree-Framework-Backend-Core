package com.config;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.*;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import com.egovframework.ple.treeframework.util.*;
public class JiraConfig {

    public static JiraRestClient getJiraRestClient() throws URISyntaxException, IOException {

        final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();

        PropertiesReader propertiesReader = new PropertiesReader("com/egovframework/property/globals.properties");
        String jiraUrl = propertiesReader.getProperty("arms.jira.baseurl");
        String jiraID = propertiesReader.getProperty("arms.jira.id");
        String jiraPass = propertiesReader.getProperty("arms.jira.pass");

        return factory.createWithBasicHttpAuthentication(new URI(jiraUrl), jiraID, jiraPass);

    }

}
