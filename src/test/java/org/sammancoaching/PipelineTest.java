package org.sammancoaching;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sammancoaching.dependencies.*;

import static org.mockito.Mockito.*;

class PipelineTest {

    // Testes para o resultado dos testes
    @Test
    @DisplayName("Deve mostrar no log 'Tests passed' quando os testes passam")
    void mensagemNoLogQuandoOsTestesPassam() {
        Config config = mock(Config.class);
        Emailer emailer = mock(Emailer.class);
        Logger logger = mock(Logger.class);
        Pipeline pipeline = new Pipeline(config, emailer, logger);

        Project project = Project.builder().setTestStatus(TestStatus.PASSING_TESTS).build();
        pipeline.run(project);
        verify(logger).info("Tests passed");
    }

    @Test
    @DisplayName("Deve mostrar no log 'Tests failed' quando os testes falham")
    void mensagemNoLogQuandoOsTestesFalham() {
        Config config = mock(Config.class);
        Emailer emailer = mock(Emailer.class);
        Logger logger = mock(Logger.class);
        Pipeline pipeline = new Pipeline(config, emailer, logger);

        Project project = Project.builder().setTestStatus(TestStatus.FAILING_TESTS).build();
        pipeline.run(project);
        verify(logger).error("Tests failed");
    }

    @Test
    @DisplayName("Deve mostrar no log 'No tests' quando não existem testes")
    void mensagemNoLogQuandoNaoExistemTestes() {
        Config config = mock(Config.class);
        Emailer emailer = mock(Emailer.class);
        Logger logger = mock(Logger.class);
        Pipeline pipeline = new Pipeline(config, emailer, logger);

        Project project = Project.builder().setTestStatus(TestStatus.NO_TESTS).build();
        pipeline.run(project);
        verify(logger).info("No tests");
    }

    // Testes do deploy
    @Test
    @DisplayName("Deve mostrar 'Deployment successful' quando deploy ocorre com sucesso")
    void deveMostrarDeploySuccessful() {
        Config config = mock(Config.class);
        Emailer emailer = mock(Emailer.class);
        Logger logger = mock(Logger.class);
        Pipeline pipeline = new Pipeline(config, emailer, logger);

        Project project = Project.builder().setTestStatus(TestStatus.PASSING_TESTS).setDeploysSuccessfully(true).build();
        pipeline.run(project);
        verify(logger).info("Deployment successful");
    }

    @Test
    @DisplayName("Deve mostrar 'Deployment failed' quando o deploy falha")
    void deveMostrarDeployFailed() {
        Config config = mock(Config.class);
        Emailer emailer = mock(Emailer.class);
        Logger logger = mock(Logger.class);
        Pipeline pipeline = new Pipeline(config, emailer, logger);

        Project project = Project.builder().setTestStatus(TestStatus.PASSING_TESTS).setDeploysSuccessfully(false).build();
        pipeline.run(project);
        verify(logger).error("Deployment failed");
    }

    // Teste de email
    @Test
    @DisplayName("Deve enviar email de sucesso quando deploy ocorre corretamente")
    void deveEnviarEmailDeSucesso() {
        Config config = mock(Config.class);
        Emailer emailer = mock(Emailer.class);
        Logger logger = mock(Logger.class);

        when(config.sendEmailSummary()).thenReturn(true);

        Pipeline pipeline = new Pipeline(config, emailer, logger);

        Project project = Project.builder().setTestStatus(TestStatus.PASSING_TESTS).setDeploysSuccessfully(true).build();
        pipeline.run(project);
        verify(emailer).send("Deployment completed successfully");
    }

    @Test
    @DisplayName("Deve enviar email informando falha no deploy")
    void deveEnviarEmailDeployFailed() {
        Config config = mock(Config.class);
        Emailer emailer = mock(Emailer.class);
        Logger logger = mock(Logger.class);

        when(config.sendEmailSummary()).thenReturn(true);

        Pipeline pipeline = new Pipeline(config, emailer, logger);

        Project project = Project.builder().setTestStatus(TestStatus.PASSING_TESTS).setDeploysSuccessfully(false).build();
        pipeline.run(project);
        verify(emailer).send("Deployment failed");
    }

    @Test
    @DisplayName("Deve enviar email informando falha nos testes")
    void deveEnviarEmailTestsFailed() {
        Config config = mock(Config.class);
        Emailer emailer = mock(Emailer.class);
        Logger logger = mock(Logger.class);

        when(config.sendEmailSummary()).thenReturn(true);

        Pipeline pipeline = new Pipeline(config, emailer, logger);

        Project project = Project.builder().setTestStatus(TestStatus.FAILING_TESTS).build();
        pipeline.run(project);
        verify(emailer).send("Tests failed");
    }

    @Test
    @DisplayName("Deve mostrar no log que o envio de email está desabilitado")
    void deveMostrarEmailDisabled() {
        Config config = mock(Config.class);
        Emailer emailer = mock(Emailer.class);
        Logger logger = mock(Logger.class);
        Pipeline pipeline = new Pipeline(config, emailer, logger);

        Project project = Project.builder().setTestStatus(TestStatus.PASSING_TESTS).build();
        pipeline.run(project);
        verify(logger).info("Email disabled");
    }
}
