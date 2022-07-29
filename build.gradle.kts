plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.spring.deps)
    alias(libs.plugins.kotlin.jvm)
}

group = "com.alesharik.spring"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")

    configurations {
        compileOnly {
            extendsFrom(annotationProcessor.get())
        }
    }

    repositories {
        mavenCentral()
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        val libs = rootProject.libs
        api(platform(libs.spring.boot.dependencies))
        compileOnly(libs.lombok)
        annotationProcessor(platform(libs.spring.boot.dependencies))
        annotationProcessor(libs.lombok)
        annotationProcessor(libs.spring.configuration.processor)
        testImplementation(libs.assertj)
        testImplementation(libs.mockito)
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    tasks.javadoc {
        if (JavaVersion.current().isJava9Compatible) {
            (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        }
    }

    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/alesharik/spring-baseinfra")
                credentials(HttpHeaderCredentials::class) {
                    name = "Authorization"
                    value = "Bearer ${project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")}"
                }
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
            }
        }
        publications {
            create<MavenPublication>("gpr") {
                from(components["java"])
            }
        }
    }
}
