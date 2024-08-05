package camp.model;

import java.lang.invoke.StringConcatFactory;
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

    private void validateRound(int round) throws ScoreException {
        if (round < 1 || round > 10) {
            throw new ScoreException("회차는 1부터 10까지의 값만 허용됩니다.");
        }
    }

    private void validateScore(int score) throws ScoreException {
        if (score < 0 || score > 100) {
            throw new ScoreException("점수는 0부터 100까지의 값만 허용됩니다.");
        }
    }

    private String determineGrade(int score) {
        if (score >= 90) return "A";
        else if (score >= 80) return "B";
        else if (score >= 70) return "C";
        else if (score >= 60) return "D";
        else return "F";
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId +
               ", Subject ID: " + subjectId +
               ", Scores: " + scores +
               ", Grades: " + grades;
    }
}
