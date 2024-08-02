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

        // 필수 과목 추가
        requiredSubjects.add("Java");
        requiredSubjects.add("객체지향");
        requiredSubjects.add("Spring");
        requiredSubjects.add("JPA");
        requiredSubjects.add("MySQL");

        // 선택 과목 추가
        electiveSubjects.add("디자인 패턴");
        electiveSubjects.add("Spring Security");
        electiveSubjects.add("Redis");
        electiveSubjects.add("MongoDB");
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
        // 회차와 점수의 유효성 검사
        if (round < 1 || round > 10) {
            throw new ScoreException("회차는 1에서 10 사이여야 합니다.");
        }
        if (score < 0 || score > 100) {
            throw new ScoreException("점수는 0에서 100 사이여야 합니다.");
        }

        subjectScores.putIfAbsent(subject, new HashMap<>());
        Map<Integer, Integer> rounds = subjectScores.get(subject);


        // 중복된 회차 점수 등록 검사
        if (rounds.containsKey(round)) {
            throw new ScoreException("이미 등록된 회차 점수입니다.");
        }

        rounds.put(round, score);

        // 등급 계산 & 저장
        String grade = calculateGrade(subject, score);
        subjectGrades.putIfAbsent(subject, new HashMap<>());
        subjectGrades.get(subject).put(round, grade);
    }

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
        }else {
            throw new ScoreException("점수를 잘못 입력하셨습니다.");
            // 예외 처리
        }
    }

    // 조건 1. 최소 3개 이상의 필수 과목, 2개 이상의 선택 과목을 선택합니다.
    public void minScore() {
        long requiredMin = subjectScores.keySet().stream()
                .filter(requiredSubjects::contains)
                .count();
        long electiveMin = subjectScores.keySet().stream()
                .filter(electiveSubjects::contains)
                .count();

        if (requiredMin < 3) {
            throw new ScoreException("최소 3개의 필수 과목을 입력해야 합니다.");
        }

        if (electiveMin < 2) {
            throw new ScoreException("최소 2개의 선택 과목을 입력해야 합니다.");
        }
    }

    // 과목별 시험 회차와 점수 조회 메서드
    public Map<Integer, Integer> getSubjectScores(String subject) {
        return subjectScores.getOrDefault(subject, new HashMap<>());
    }
    // 과목별 시험 회차와 등급 조회 메서드
    public Map<Integer, String> getSubjectGrades(String subject) {
        return subjectGrades.getOrDefault(subject, new HashMap<>());
    }


    public static void main(String[] args) {
        Score score = new Score("001");

        // 점수 등록 및 예외 처리
        try {
            score.addScore("Java", 1, 85);
        } catch (ScoreException e) {
            System.out.println("오류: " + e.getMessage());
        }
        try {
            score.addScore("디자인 패턴", 5, 120);
        } catch (ScoreException e) {
            System.out.println("오류: " + e.getMessage());
        }

        // 결과 출력
        System.out.println("Java 점수: " + score.getSubjectScores("Java"));
        System.out.println("Java 등급: " + score.getSubjectGrades("Java"));
        System.out.println("객체지향 점수: " + score.getSubjectScores("객체지향"));
        System.out.println("객체지향 등급: " + score.getSubjectGrades("객체지향"));
    }
}