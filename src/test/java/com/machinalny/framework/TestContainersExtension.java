package com.machinalny.framework;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.DockerComposeContainer;

public class TestContainersExtension implements BeforeAllCallback, AfterAllCallback {

    private static boolean started = false;
    private DockerComposeContainer dockerComposeContainer = new DockerComposeContainer(
            new java.io.File("src/test/resources/docker-compose.yaml"))
            .withLocalCompose(true);

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        if (!started) {
            dockerComposeContainer.start();
            started = true;
        }
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        if (started) {
            dockerComposeContainer.stop();
            started = false;
        }
    }
}
