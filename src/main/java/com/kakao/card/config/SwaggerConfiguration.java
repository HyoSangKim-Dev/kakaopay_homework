package com.kakao.card.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	/**
	 * 출처 : https://jamong-icetea.tistory.com/373
	 *      https://steemit.com/kr-dev/@igna84/spring-boot-web-swagger
	 */
	
	/**
	 * 필수항목
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getApiInfo()) // 스웨거 정보 등록
				.select() 
				.apis(RequestHandlerSelectors.basePackage("com.kakao.card.api.controller")) // 모든 controller들이 있는 패키지를 탐색해서 API문서를 만든다.
				.paths(PathSelectors.any())
				.build()
				.useDefaultResponseMessages(true); // 기본으로 세팅되는 200, 401, 403, 404 메시지 표시
	}
	
	/**
	 * 선택항목(Swagger UI에서 보여지는 정보)
	 */
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
				.title("카드사와 통신하는 API")
				.description("kakao 보험 사전과제")
				.version("1.0.0")
				.build();
	}
}