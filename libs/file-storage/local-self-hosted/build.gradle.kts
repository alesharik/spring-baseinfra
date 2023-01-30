version = "1.0.2"

dependencies {
    api(project(":file-storage-api"))
    implementation(libs.spring.web)
    implementation(libs.spring.boot)
    implementation(libs.spring.boot.autoconfigure)
    implementation(libs.swagger.annotations)
    testImplementation(libs.spring.starter.test)
}
