package org.kesler.client;

import javafx.fxml.FXMLLoader;
import org.kesler.client.gui.cause.CauseController;
import org.kesler.client.gui.check.ChecksController;
import org.kesler.client.gui.main.MainController;
import org.kesler.client.gui.branch.BranchController;
import org.kesler.client.gui.branch.BranchListController;
import org.kesler.client.service.*;
import org.kesler.client.service.support.BranchRestServiceImpl;
import org.kesler.client.service.support.CauseRestServiceImpl;
import org.kesler.client.service.support.CheckRestServiceImpl;
import org.kesler.client.service.support.RecordRestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A Spring configuration class that provides factory methods for all the main components that make up the JavaFX client
 * application. This is used by the main App class on startup to load beans in a way that allows them to be autowired
 * into each other appropriately, along with all the other benefits of using Spring.
 * <p/>
 * Unless otherwise marked, all beans provided by this factory are singletons. So if you use an ApplicationContext for
 * loading this factory (as is done in the main App of this project), then each call to get a bean will return the same
 * instance. For example, the RestTemplate created below may get used in multiple services but only one instance will
 * ever be created and shared.
 * <p/>
 * This class is a direct replacement for the normal Spring XML file so in client side JavaFX we don't need this the
 * XML configuration file at all, just this.
 * <p/>
 * For more information on Spring configuration see:
 * http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/beans.html#beans-java
 */
@Configuration
@PropertySource(value="file:config/PVDConnect-client.properties")
public class SimpleRestAppFactory {

    /**
     * Spring will wire this up to the properties file defined by the @PropertySource definition on this class. This
     * allows us to get access to our configuration properties in a fairly clean way.
     */
    @Autowired
    private Environment env;

    /**
     * Factory method for creating a RestTemplate, which is a Spring helper class that simplifies making Rest calls onto
     * a remote server. See http://blog.springsource.com/2009/03/27/rest-in-spring-3-resttemplate/ for more information
     * on RestTemplates.
     *
     * @return a RestTemplate ready for use in the
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new MappingJacksonHttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

    @Bean
    public MainController mainController() {
        MainController mainController = loadController("/fxml/Main.fxml");
        mainController.setConfig("yes".equals(env.getProperty("app.config")));
        return mainController;
    }

    @Bean
    public BranchController branchController() {
        return loadController("/fxml/Branch.fxml");
    }

    @Bean
    public BranchListController branchListController() {
        return loadController("/fxml/BranchList.fxml");
    }

    @Bean
    public ChecksController checksController() {
        return loadController("/fxml/Checks.fxml");
    }

    @Bean
    public CauseController causeController() {
        return loadController("/fxml/Cause.fxml");
    }

    /**
     * Creates a WelcomeRestService which provides a nice wrapper around the REST calls the server, encapsulating all
     * the server calls and String manipulations in one place.
     * <p/>
     * Note that the WelcomeRestService defines a reference to the RestTemplate using the @Autowired annotation. As a
     * result the RestTemplate created by the method above will be automatically injected into this repository when it is
     * created.
     *
     * @return the WelcomeRestService which provides a convenient facade for calling server side REST calls.
     */


    @Bean
    public BranchService branchService() {
        return new BranchRestServiceImpl(env.getProperty("server.url"));
    }

    @Bean
    public RecordService recordRestService() {
        return new RecordRestServiceImpl(env.getProperty("server.url"));
    }

    @Bean
    public CheckService checkService() {
        return new CheckRestServiceImpl(env.getProperty("server.url"));
    }

    @Bean
    public CauseService causeService() {
        return new CauseRestServiceImpl(env.getProperty("server.url"));
    }

    /**
     * Convenience method for loading Controllers from FXML. FXML can be a little impure in its inter-dependencies
     * between client and server (it is quite biased to things being view driven and tightly couples the view into its
     * controller, etc). We make things a little cleaner by interacting mostly with the Controller and only accessing
     * the View via it. This load method hooks reverses the focus of the FXMLLoader to make things Controller based.
     *
     * @param fxmlFile the file to load the FXML from, this should be relative to the classpath.
     * @param <T> the type of Controller to return is inferred by whatever you assign the result of this method to.
     * @return the Controller loaded from the FXML specified which should have its view loaded and attached.
     */
    private <T> T loadController(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            loader.load();
            return (T) loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Unable to load FXML file '%s'", fxmlFile), e);
        }
    }
}
