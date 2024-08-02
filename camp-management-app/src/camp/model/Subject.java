package camp.model;

public class Subject {
    private String subjectId;
    private String subjectName;
    private String subjectType;

    public Subject(String subjectId, String subjectName, String subjectType) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectType = subjectType;
    }

    // Getter
    public String getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectType() {
        return subjectType;
    }

}
