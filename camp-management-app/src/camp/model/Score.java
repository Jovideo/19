package camp.model;

import java.util.HashMap;
import java.util.Map;

public class Score {
    private String scoreId;
    private String studentId;
    private String subjectId;
    private Map<Integer, Integer> scores; // 회차와 점수 저장
    private Map<Integer, String> grades;  // 회차와 등급 저장

    public Score(String scoreId, String studentId, String subjectId) {
        this.scoreId = scoreId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.scores = new HashMap<>();
        this.grades = new HashMap<>();
    }

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Map<Integer, Integer> getScores() {
        return scores;
    }

    public Map<Integer, String> getGrades() {
        return grades;
    }

    // 점수를 추가하거나 업데이트하는 메서드
    public void addScore(int round, int score) throws ScoreException {
        validateRound(round);
        validateScore(score);

        if (scores.containsKey(round)) {
            throw new ScoreException("회차 " + round + "의 점수는 이미 등록되어 있습니다.");
        }

        scores.put(round, score);
        String grade = determineGrade(score);
        grades.put(round, grade);
    }

    // 점수를 업데이트하는 메서드
    public void updateScore(int round, int score) throws ScoreException {
        validateRound(round);
        validateScore(score);

        if (!scores.containsKey(round)) {
            throw new ScoreException("회차 " + round + "의 점수가 등록되어 있지 않습니다.");
        }

        scores.put(round, score);
        String grade = determineGrade(score);
        grades.put(round, grade);
    }

    // 회차 유효성 검사를 위한 메서드
    private void validateRound(int round) throws ScoreException {
        if (round < 1 || round > 10) {
            throw new ScoreException("회차는 1부터 10까지의 값만 허용됩니다.");
        }
    }

    // 점수 유효성 검사를 위한 메서드
    private void validateScore(int score) throws ScoreException {
        if (score < 0 || score > 100) {
            throw new ScoreException("점수는 0부터 100까지의 값만 허용됩니다.");
        }
    }

    // 필수 과목의 등급 결정
    private String determineMandatoryGrade(int score) {
        if (score >= 95) return "A";
        else if (score >= 90) return "B";
        else if (score >= 80) return "C";
        else if (score >= 70) return "D";
        else if (score >= 60) return "F";
        else return "N";
    }

    // 선택 과목의 등급 결정
    private String determineChoiceGrade(int score) {
        if (score >= 90) return "A";
        else if (score >= 80) return "B";
        else if (score >= 70) return "C";
        else if (score >= 60) return "D";
        else if (score >= 50) return "F";
        else return "N";
    }

    // 과목에 따라 적절한 등급 결정 메서드
    private String determineGrade(int score) {
        // 필수 과목과 선택 과목을 구분하여 등급을 결정합니다.
        if (isMandatorySubject()) {
            return determineMandatoryGrade(score);
        } else {
            return determineChoiceGrade(score);
        }
    }

    // 과목이 필수 과목인지 여부를 확인하는 메서드
    private boolean isMandatorySubject() {
        return subjectId.startsWith("MANDATORY_");
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId +
               ", Subject ID: " + subjectId +
               ", Scores: " + scores +
               ", Grades: " + grades;
    }
}
