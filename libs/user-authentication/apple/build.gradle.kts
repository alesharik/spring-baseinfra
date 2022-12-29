version = "1.1.2"

dependencies {
    api(project(":user-authentication-exceptions"))
    implementation(libs.swagger.annotations)
    implementation(libs.spring.web)
    implementation(libs.spring.security.jose)
    implementation(libs.nimbus.jwt)
}
