package tv.mangrana.sonarr.api.client.gateway;

import tv.mangrana.sonarr.api.schema.command.RefreshSerieCommand;
import tv.mangrana.sonarr.api.schema.history.SonarrHistory;
import tv.mangrana.sonarr.api.schema.queue.SonarrQueue;
import tv.mangrana.sonarr.api.schema.series.SonarrSerie;
import tv.mangrana.utils.rest.APIInterface;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * For more information, visit: <a href="https://github.com/Sonarr/Sonarr/wiki/API">official Documentation</a>
 */
@Path("/api/v3")
public interface SonarrAPIInterface extends APIInterface {

    @GET
    @Path("/queue")
    @Produces({ MediaType.APPLICATION_JSON })
    SonarrQueue getFirstPageQueue(@QueryParam("apikey") String apikey);

    @GET
    @Path("/queue")
    @Produces({ MediaType.APPLICATION_JSON })
    SonarrQueue getQueue(@QueryParam("apikey") String apikey, @QueryParam("pageSize") int pageSize);

    @DELETE
    @Path("/queue/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    void deleteQueueElement(@PathParam("id") Integer idElement, @QueryParam("removeFromClient") boolean removeFromClient,
                            @QueryParam("apikey") String apikey);

    @GET
    @Path("/series/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    SonarrSerie getSerieById(@PathParam("id") Integer idSerie, @QueryParam("apikey") String apikey);

    @GET
    @Path("/series")
    @Produces({ MediaType.APPLICATION_JSON })
    List<SonarrSerie> serieLookupByTVDBid(@QueryParam("tvdbId") int tvdbId, @QueryParam("apikey") String apikey);

    @POST
    @Path("/command")
    @Consumes({ MediaType.APPLICATION_JSON })
    void refreshSeriesCommand(RefreshSerieCommand command, @QueryParam("apikey") String apikey);

    /**
     * Get history. <a href="https://github.com/Sonarr/Sonarr/wiki/History">see here the official Documentation</a>
     * @param sortKey (REQUIRED, string) - series.title or date
     * @param page (int) - 1-indexed Default: 1
     * @param pageSize (int) - Default: 10
     * @param sortDir (string) - asc or desc - Default: desc
     */
    @GET
    @Path("/history")
    @Consumes({ MediaType.APPLICATION_JSON })
    SonarrHistory getHistory(@QueryParam("sortKey") String sortKey, @QueryParam("sortDir") String sortDir,
                             @QueryParam("pageSize") int pageSize, @QueryParam("page") int page,
                             @QueryParam("apikey") String apikey);

    @PUT
    @Path("/series/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    void updateSerie(SonarrSerie serie, @PathParam("id") int serieId, @QueryParam("apikey") String apikey);


}