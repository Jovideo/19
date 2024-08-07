package camp.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentId;
    private String studentName;
    private StudentStatus status;
    private List<Subject> mandatorySubjects;
    private List<Subject> choiceSubjects;

    public Student(String seq, String studentName, List<Subject> mandatorySubjects, List<Subject> choiceSubjects) {
        this.studentId = seq;
        this.studentName = studentName;
        this.mandatorySubjects = mandatorySubjects;
        this.choiceSubjects = choiceSubjects;
        this.status = StudentStatus.GREEN;
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

    // state Getter 메서드 추가
    public StudentStatus getStatus(){
        return status;
    }

    // state Setter 메서드 추가
    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    // 과목이 포함되어 있는지 확인
    public boolean hasSubject(Subject subject){
        return mandatorySubjects.contains(subject) || choiceSubjects.contains(subject);
    }

    // 선택한 모든 과목을 반환
    public List<Subject> getAllSubjects(){
        List<Subject> allSubjects = new ArrayList<>(mandatorySubjects);
        allSubjects.addAll(choiceSubjects);
        return allSubjects;
    }

    @Override
    public String toString() {
        return "ID: " + studentId +
                ", 이름: " + studentName +
                ", 필수 과목: " + formatSubjects(mandatorySubjects) +
                ", 선택 과목: " + formatSubjects(choiceSubjects)+
                ", 상태: "+status.getDescription();
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
