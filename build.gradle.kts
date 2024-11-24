plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.hyfata.sonichub"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://m2.chew.pro/snapshots") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("commons-io:commons-io:2.15.1")
    implementation("org.jline:jline:3.25.1")
    implementation("ch.qos.logback:logback-classic:1.4.11") // Do not change this!

    implementation("net.dv8tion:JDA:5.2.1")
    implementation("pw.chew:jda-chewtils:2.0-SNAPSHOT")

    implementation("com.github.najoan125:FileUtils:1.1.0")
    implementation("org.json:json:20240303")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.hyfata.sonichub.SonicHub"
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>{
    options.encoding = "UTF-8"
}