package currencyconverter.core.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("currencyconverter.core"))
                .paths(PathSelectors.ant("/**"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .apiInfo(getApiDetails());
    }

    private ApiInfo getApiDetails() {
        return new ApiInfoBuilder()
                .title("Currency converter APP")
                .description("convert currency")
                .version("0.001")
                .build();
    }
}
