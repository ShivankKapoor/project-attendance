package record;

import java.sql.Date;
import java.util.List;

public record Records (List<Records> recordsList) {
    public record Checkin(String stdId, Date date) { }

}


