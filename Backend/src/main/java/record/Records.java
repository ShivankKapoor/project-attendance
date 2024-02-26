package record;

import java.util.List;

public record Records (List<Records> recordsList) {
    public record Checkin(String courseId, int utdId, String netId) { }

}


