package record;

import java.util.List;

public record Records (List<Records> recordsList) {
    public record Checkin(String courseId, int utdId, String password) { }

    public record register(String fullName, String netId, String utdId, String password) { }

    public record login(String utdId, String password) { }

    public record courseInfo(String classId, String courseId, String className) { }

    public record getAllClassesStudent(int utdId) { }

    public record getCourseId(String courseId) { }

    public record timings(String startDate, String startTime, String timeBuffer) { }

}


