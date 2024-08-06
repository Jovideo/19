package camp.model;

import java.util.List;

public class Student {
    private String studentId;
    private String studentName;
    private List<Subject> mandatorySubjects;
    private List<Subject> choiceSubjects;

    public Student(String seq, String studentName, List<Subject> mandatorySubjects, List<Subject> choiceSubjects) {
        this.studentId = seq;
        this.studentName = studentName;
        this.mandatorySubjects = mandatorySubjects;
        this.choiceSubjects = choiceSubjects;
    }

    // Getter
    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public List<Subject> getMandatorySubjects() {
        return mandatorySubjects;
    }

    public List<Subject> getChoiceSubjects() {
        return choiceSubjects;
    }

    @Override
    public String toString() {
        return "ID: " + studentId +
                ", 이름: " + studentName +
                ", 필수 과목: " + formatSubjects(mandatorySubjects) +
                ", 선택 과목: " + formatSubjects(choiceSubjects);
    }

    private String formatSubjects(List<Subject> subjects) {
        if (subjects == null || subjects.isEmpty()) {
            return "없음";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < subjects.size(); i++) {
            sb.append(subjects.get(i).getSubjectName());
            if (i < subjects.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

}
