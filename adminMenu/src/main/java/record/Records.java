package record;

import java.util.List;


public record Records(List<Records> recordsList) {
    public record professorCheckin(String classId, String password, int timeBuffer) { }


}


