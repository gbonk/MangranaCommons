package tv.mangrana.sonarr.api.schema.queue;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QueueBulk {
    @JsonProperty("ids")
    List<Integer> ids;

    public QueueBulk(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return ids;
    }
}
