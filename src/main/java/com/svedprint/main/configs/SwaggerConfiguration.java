//package com.svedprint.main.configs;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import springfox.documentation.builders.OAuthBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger.web.ApiKeyVehicle;
//import springfox.documentation.swagger.web.SecurityConfiguration;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.*;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfiguration {
//	private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);
//
//	@Value("${security.jwt.client-secret}")
//	private String clientSecret;
//
//	@Value("${security.jwt.client-id}")
//	private String clientId;
//
//	@Value("${server.location}")
//	private String baseLocation;
//
//	@Bean
//	public Docket swaggerSpringfoxDocket() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select().apis(RequestHandlerSelectors.basePackage("com.yugioh.yugioh"))
//				.paths(PathSelectors.any()).build().pathMapping("/")
//				.genericModelSubstitutes(ResponseEntity.class)
//				.ignoredParameterTypes(Pageable.class)
//				.ignoredParameterTypes(java.sql.Date.class)
//				.directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
//				.directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
//				.directModelSubstitute(java.time.LocalDateTime.class, Date.class)
//				.useDefaultResponseMessages(false)
//				.apiInfo(apiInfo())
//				.securityContexts(Collections.singletonList(securityContext()))
//				.securitySchemes(securitySchemes());
//	}
//
//	private SecurityContext securityContext() {
//		return SecurityContext.builder()
//				.securityReferences(Collections.singletonList(new SecurityReference("Yu-Gi-Oh Login", scopes())))
//				.forPaths(PathSelectors.ant("/api/account/register").negate()
//						.and(PathSelectors.ant("/api/**")))
//				.build();
//	}
//
//	private AuthorizationScope[] scopes() {
//		return new AuthorizationScope[]{
//				new AuthorizationScope("read", "for read operations"),
//				new AuthorizationScope("write", "for write operations")
//		};
//	}
//
//	private List<SecurityScheme> securitySchemes() {
//		GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(baseLocation + "/oauth/token");
//
//		SecurityScheme oauth = new OAuthBuilder().name("Yu-Gi-Oh Login")
//				.grantTypes(Collections.singletonList(grantType))
//				.scopes(Arrays.asList(scopes()))
//				.build();
//
//		return Arrays.asList(oauth, new ApiKey("Bearer", "Authorization", "header"));
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfo("Yu-Gi-Oh REST API", "REST API for retrieving cards & login information.",
//				"1.0.0", "Terms of service",
//				new Contact("Kristijan Kostovski", "", "kristijan.kostovski@students.finki.ukim.mk"),
//				"", "", new ArrayList<>());
//	}
//
//	@Bean
//	public SecurityConfiguration securityInfo() {
//		return new SecurityConfiguration(clientId, clientSecret, "", "", "", ApiKeyVehicle.HEADER, "", " ");
//	}
//}
