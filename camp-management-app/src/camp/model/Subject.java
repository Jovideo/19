package camp.model;

import java.util.Objects;

public class Subject {
    private String subjectId;
    private String subjectName;
    private SubjectType subjectType;

    public Subject(String seq, String subjectName, SubjectType subjectType) {
        this.subjectId = seq;
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

    public SubjectType getSubjectType() {
        return subjectType;
    }

    @Override
    public String toString() {
        return "과목이름='" + subjectName + "'" +
                ", 과목타입='" + subjectType + "'";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(subjectId, subject.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId);
    }
}