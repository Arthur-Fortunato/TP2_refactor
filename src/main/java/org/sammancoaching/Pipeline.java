package org.sammancoaching;

import org.sammancoaching.dependencies.Config;
import org.sammancoaching.dependencies.Emailer;
import org.sammancoaching.dependencies.Logger;
import org.sammancoaching.dependencies.Project;

public class Pipeline {
    private final Config config;
    private final Emailer emailer;
    private final Logger log;
    private static final String SUCCESS = "success";

    public Pipeline(Config config, Emailer emailer, Logger log) {
        this.config = config;
        this.emailer = emailer;
        this.log = log;
    }

    public void run(Project project) {
        boolean testsPassed = areTestsPassing(project);
        boolean deploySuccessful = isDeploySuccessful(project, testsPassed);
        sendEmail(testsPassed, deploySuccessful);
    }

    private boolean areTestsPassing(Project project) {
        if (!project.hasTests()) {
            log.info("No tests");
            return true;
        }
        if (SUCCESS.equals(project.runTests())) {
            log.info("Tests passed");
            return true;
        }
        log.error("Tests failed");
        return false;
    }

    private boolean isDeploySuccessful(Project project, boolean testsPassed) {
        if (!testsPassed) {
            return false;
        }
        if (SUCCESS.equals(project.deploy())) {
            log.info("Deployment successful");
            return true;
        }
        log.error("Deployment failed");
        return false;
    }

    private void sendEmail(boolean testsPassed, boolean deploySuccessful) {
        if (!config.sendEmailSummary()) {
            log.info("Email disabled");
        }
        log.info("Sending email");
        if (!testsPassed) {
            emailer.send("Tests failed");
            return;
        }
        if (deploySuccessful) {
            emailer.send("Deployment completed successfully");
        } else {
            emailer.send("Deployment failed");
        }
    }
}