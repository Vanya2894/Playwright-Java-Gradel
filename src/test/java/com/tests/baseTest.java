package com.tests;

import com.google.common.collect.ImmutableMap;
import com.microsoft.playwright.*;
import com.pages.BasePage;
import com.pages.LoginPage;
import com.utils.BasePageFactory;
import com.utils.BrowserManager;
import io.qameta.allure.Attachment;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static com.config.ConfigurationManager.config;
import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;


public abstract class baseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected LoginPage loginPage;
    private boolean needVideo;


    @RegisterExtension
    AfterTestExecutionCallback callback =
            context -> {
                Optional<Throwable> exception = context.getExecutionException();
                if (exception.isPresent()) {
                    needVideo = false;
                    captureScreenshotOnFailure();
                }
            };

    @BeforeAll
    public static void initBrowser() {
        playwright = Playwright.create();
        browser = BrowserManager.getBrowser(playwright);

        allureEnvironmentWriter(ImmutableMap.<String, String>builder()
                        .put("Platform", System.getProperty("os.name"))
                        .put("Version", System.getProperty("os.version"))
                        .put("Browser", config().browser().toUpperCase())
                        .put("Context URL", config().baseUrl())
                        .build(),
                config().allureResultsDir() + "/");
    }

    @BeforeEach
    public void createContext() {
        if (browser == null) {
            throw new RuntimeException("Browser is null! Проверь BrowserManager и конфигурацию.");
        }

        try {
            // Создаём контекст с записью видео, если включено
            if (config().video()) {
                String videoPath = config().baseTestVideoPath();
                if (videoPath == null || videoPath.isEmpty()) {
                    throw new RuntimeException("Video path пустой. Проверь config().baseTestVideoPath()");
                }

                System.out.println("Creating browser context with video recording at: " + videoPath);
                browserContext = browser.newContext(
                        new Browser.NewContextOptions()
                                .setRecordVideoDir(Paths.get(videoPath))
                );
            } else {
                System.out.println("Creating normal browser context...");
                browserContext = browser.newContext();
            }

            if (browserContext == null) {
                throw new RuntimeException("Не удалось создать BrowserContext. Проверь конфигурацию Playwright.");
            }

            page = browserContext.newPage();
            if (page == null) {
                throw new RuntimeException("Не удалось создать Page из browserContext.");
            }

            loginPage = createInstance(LoginPage.class);
            if (loginPage == null) {
                throw new RuntimeException("Не удалось создать LoginPage. Проверь BasePageFactory и конструктор класса.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create browser context/page: " + e.getMessage(), e);
        }
    }

    @AfterEach
    public void attach() {
        browserContext.close();
        if (config().video() && needVideo) {
            captureVideo();
        }
        needVideo = false;
    }

    @AfterAll
    public static void close() {
        browser.close();
        playwright.close();
    }


    @Attachment(value = "Test Video", type = "video/webm")
    @SneakyThrows
    private byte[] captureVideo() {
        return Files.readAllBytes(page.video().path());
    }

    @Attachment(value = "Failed test Case Screenshot", type = "image/png")
    private byte[] captureScreenshotOnFailure() {
        return page.screenshot();
    }

    protected <T extends BasePage> T createInstance(Class<T> basePage) {
        return BasePageFactory.createInstance(page, basePage);
    }


}
