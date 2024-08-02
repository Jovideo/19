package camp.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Score {
    private String scoreId;
    private Map<String, Map<Integer, Integer>> subjectScores; // 과목별로 시험 회차와 점수를 저장하는 맵
    private Map<String, Map<Integer, String>> subjectGrades; // 과목별로 시험 회차와 등급을 저장하는 맵
    private Set<String> requiredSubjects; // 필수 과목 목록
    private Set<String> electiveSubjects; // 선택 과목 목록

    public Score(String seq) {
        // this 키워드를 통해 변수명에 해당하는 객체의 필드에 접근하여 받아온 매개변수의 매개값을 객체의 필드에 대입하여 저장할 수 있다.
        this.scoreId = seq;
        this.subjectScores = new HashMap<>();
        this.subjectGrades = new HashMap<>();
        this.requiredSubjects = new HashSet<>();
        this.electiveSubjects = new HashSet<>();
    }

    // Getter
    public String getScoreId() {
        // private 접근 제어자로 지정한 필드를 Getter 메서드를 통해 값을 가져올 수 있다.
        return scoreId;
    }

    // 필수 과목 추가 메서드
    public void addRequiredSubject(String subject) {
        requiredSubjects.add(subject);
    }

    // 선택 과목 추가 메서드
    public void addElectiveSubject(String subject) {
        electiveSubjects.add(subject);
    }

    // 점수 등록 메서드
    public void addScore(String subject, int round, int score) {

    }

    // 중복된 회차 점수 등록 검사


    // 등급 계산 & 저장


    // 점수에 따른 등급 계산 메서드
    private String calculateGrade(String subject, int score) {
        if (requiredSubjects.contains(subject)) {
            // 필수 과목 등급 기준
            if (score >= 95) {
                return "A";
            } else if (score >= 90) {
                return "B";
            } else if (score >= 80) {
                return "c";
            } else if (score >= 70) {
                return "D";
            } else if (score >= 60) {
                return "F";
            } else {
                return "N";
            }
        } else if (electiveSubjects.contains(subject)) {
            // 선택 과목 등급 기준
            if (score >= 90) {
                return "A";
            } else if (score >= 80) {
                return "B";
            } else if (score >= 70) {
                return "C";
            } else if (score >= 60) {
                return "D";
            } else if (score >= 50) {
                return "F";
            } else {
                return "N";
            }
        }

        // 과목별 시험 회차와 점수 조회 메서드


        // 과목별 시험 회차와 등급 조회 메서드


        return subject;
    }
}