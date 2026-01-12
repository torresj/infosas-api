package com.torresj.infosas.configurations;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class SwaggerConfig {

  @Value("${info.app.version}")
  private final String version;

  @Bean
  public OpenAPI springOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("SAS info API")
                .description("SAS info API")
                .version(version)
                .license(
                    new License()
                        .name("GNU General Public License V3.0")
                        .url("https://www.gnu.org/licenses/gpl-3.0.html")));
  }
}