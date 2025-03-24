package tv.mangrana.utils.rest;

import java.util.Objects;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import tv.mangrana.radarr.api.client.gateway.RadarrAPIInterface;
import tv.mangrana.sonarr.api.client.gateway.SonarrAPIInterface;
import tv.mangrana.utils.Output;

public class APIProxyBuilderSingleton {

    private static RadarrAPIInterface radarrAPIInterface = null;
    private static SonarrAPIInterface sonarrAPIInterface = null;
    private APIProxyBuilderSingleton(){}

    private static void init (String host, Class<? extends APIInterface> clazz) {
        Output.log("Initializing Proxy for host "+ host + " ...");
        UriBuilder fullPath = UriBuilder.fromPath(APIInterface.ProtocolURLMark.HTTPS.getMark()+host+":8989");
        ResteasyClient client = (ResteasyClient) ClientBuilder.newClient();
        ResteasyWebTarget target = client.target(fullPath);
        APIInterface apiInterface = target.proxy(clazz);
        if (clazz.getName().equals(RadarrAPIInterface.class.getName())) {
            radarrAPIInterface = (RadarrAPIInterface) apiInterface;
        } else if (clazz.getName().equals(SonarrAPIInterface.class.getName())) {
            sonarrAPIInterface = (SonarrAPIInterface) apiInterface;
        }
    }

    public static RadarrAPIInterface getRadarrInterface(String host) {
        if (Objects.isNull(radarrAPIInterface))
            init(host, RadarrAPIInterface.class);
        return radarrAPIInterface;
    }

    public static SonarrAPIInterface getSonarrInterface(String host) {
        if (Objects.isNull(sonarrAPIInterface))
            init(host, SonarrAPIInterface.class);
        return sonarrAPIInterface;
    }

}

