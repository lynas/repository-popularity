plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.sonarqube") version "6.0.1.5171"
}

group = "com.lynas.redcare"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
	}
}

jacoco {
	toolVersion = "0.8.13"
}

sonar {
	properties {
		property("sonar.projectKey", "sazzad-islam-eu_repository-popularity")
		property("sonar.organization", "sazzad-islam-eu")
		property("sonar.host.url", "https://sonarcloud.io")
		val exclusions = listOf(
			"**/config/**/*.java",
			"**/dto/**/*.java",
			"**/**/ControllerAdvise.java",
		)
		property("sonar.coverage.exclusions", exclusions)
	}
}
