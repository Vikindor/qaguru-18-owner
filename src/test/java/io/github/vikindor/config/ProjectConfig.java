package io.github.vikindor.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({"classpath:${env}.properties"})
public interface ProjectConfig extends Config {

    @Key("userName")
    String userName();

    @Key("userPassword")
    String userPassword();

    @Key("remoteUrl")
    String remoteUrl();

    @Key("browser")
    String browser();

    @Key("browserVersion")
    String browserVersion();

    @Key("browserSize")
    String browserSize();

    @Key("baseUrl")
    String baseUrl();
}
