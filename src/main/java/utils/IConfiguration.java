package utils;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:test.properties",
        "classpath:url.properties"})
public interface IConfiguration extends Config {

    @Key("baseUri")
    String baseUri();

    @Key("timeout")
    int timeout();

    @Key("createBookingPath")
    String createBookingPath();
}
