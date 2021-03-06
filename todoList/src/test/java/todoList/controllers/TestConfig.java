package todoList.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import todoList.repositories.ToDoItemRepository;
import todoList.repositories.UserRepository;
import todoList.services.CustomUserDetailsService;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    public MainController mainController() {
        return new MainController(toDoItemRepository());
    }

    @Bean
    public AddItemController addItemController() {
        return new AddItemController(toDoItemRepository(), userRepository());
    }

    @Bean
    public ToDoItemRepository toDoItemRepository() {
        return mock(ToDoItemRepository.class);
    }

    @Bean
    public UserRepository userRepository() {
        return mock(UserRepository.class);
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return mock(CustomUserDetailsService.class);
    }

    @Bean
    public ViewResolver viewResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("XHTML");
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(engine);
        return viewResolver;
    }

}
