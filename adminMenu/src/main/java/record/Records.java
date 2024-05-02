package record;

import java.util.List;


public record Records(List<Records> recordsList) {
    public record professorCheckin(String courseId, String password, int timeBuffer) { }
    public record course(String course, String name, String professor) { }

    public record courseInfo(String classId, String courseId, String className) { }

    public record daysPresent(int utdId, int daysPresent, String name) { }

    public record newUser(int utdId, String Name, String netId) {}


    public record newId(int utdId, String Name) {}

}


