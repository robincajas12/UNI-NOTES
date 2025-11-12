plugins {
    id("java")
    application
}

group = "com.progAvanzada"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // JUnit
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // CDI (Weld SE)
    implementation("org.jboss.weld.se:weld-se-core:6.0.3.Final")

    // Opcional: Jandex (útil si haces scanning)
    implementation("io.smallrye:jandex:3.2.3")
}

application {
    mainClass = "com.progAvanzada.Ejemplo01Main"
}

sourceSets{
    main{
        output.setResourcesDir(file("${buildDir}/classes/java/main"))
    }
}

tasks.test {
    useJUnitPlatform()
}
