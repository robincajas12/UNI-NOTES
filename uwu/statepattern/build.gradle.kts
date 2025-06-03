plugins {
    id("java")
}

group = "com"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.8")
    implementation(libs.guava)
    implementation("com.zaxxer:HikariCP:6.3.0")


}

tasks.test {
    useJUnitPlatform()
}