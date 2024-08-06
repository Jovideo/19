package camp;

import camp.model.Score;
import camp.model.ScoreException;
import camp.model.Student;
import camp.model.Subject;

import java.util.*;

public class CampManagementApplication {
    // 데이터 저장소
    private static List<Student> studentStore = new ArrayList<>();
    private static List<Subject> subjectStore = new ArrayList<>();
    private static List<Score> scoreStore = new ArrayList<>();

    // 과목 타입
    private static final String SUBJECT_TYPE_MANDATORY = "필수과목";
    private static final String SUBJECT_TYPE_CHOICE = "선택과목";

    // index 관리 필드
    private static int studentIndex = 0;
    private static final String INDEX_TYPE_STUDENT = "ST";
    private static int subjectIndex = 0;
    private static final String INDEX_TYPE_SUBJECT = "SU";
    private static int scoreIndex = 0;
    private static final String INDEX_TYPE_SCORE = "SC";

    // 스캐너
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        setInitData();
        try {
            displayMainView();
        } catch (Exception e) {
            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
        }
    }

    // 초기 데이터 생성
    private static void setInitData() {
        subjectStore = List.of(
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Java",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "객체지향",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Spring",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "JPA",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "MySQL",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "디자인 패턴",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Spring Security",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Redis",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "MongoDB",
                        SUBJECT_TYPE_CHOICE
                )
        );
    }

    // index 자동 증가
    private static String sequence(String type) {
        switch (type) {
            case INDEX_TYPE_STUDENT -> {
                studentIndex++;
                return INDEX_TYPE_STUDENT + studentIndex;
            }
            case INDEX_TYPE_SUBJECT -> {
                subjectIndex++;
                return INDEX_TYPE_SUBJECT + subjectIndex;
            }
            default -> {
                scoreIndex++;
                return INDEX_TYPE_SCORE + scoreIndex;
            }
        }
    }

    private static void displayMainView() throws InterruptedException {
        boolean flag = true;
        while (flag) {
            System.out.println("\n==================================");
            System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...");
            System.out.println("1. 수강생 관리");
            System.out.println("2. 점수 관리");
            System.out.println("3. 프로그램 종료");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> displayStudentView(); // 수강생 관리
                case 2 -> displayScoreView(); // 점수 관리
                case 3 -> flag = false; // 프로그램 종료
                default -> {
                    System.out.println("잘못된 입력입니다.\n되돌아갑니다!");
                    Thread.sleep(2000);
                }
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }

    private static void displayStudentView() {
        boolean flag = true;
        while (flag) {
            System.out.println("==================================");
            System.out.println("수강생 관리 실행 중...");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 목록 조회");
            System.out.println("3. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createStudent(); // 수강생 등록
                case 2 -> inquireStudent(); // 수강생 목록 조회
                case 3 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    // 수강생 등록
    private static void createStudent() {
        System.out.println("\n수강생을 등록합니다...");

        // 수강생 이름
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.next();

        // 개행문자 처리
        sc.nextLine();

        // 필수 과목 입력
        List<Subject> mandatorySubjects = getSubjectsByType(SUBJECT_TYPE_MANDATORY);
        List<Subject> selectedMandatorySubjects = new ArrayList<>();
        while (selectedMandatorySubjects.size() < 3) {
            System.out.println("필수 과목(Java, 객체지향, Spring, JPA, MySQL)을 입력해주세요.");
            String[] inputSubjects = sc.nextLine().split(",");

            selectedMandatorySubjects = getValidSubjects(mandatorySubjects, inputSubjects);

            System.out.println(selectedMandatorySubjects);
            // 필수 과목 3개 이상 입력했는지 확인
            if (selectedMandatorySubjects.size() < 3) {
                System.out.println("필수 과목은 최소 3개 이상 선택해야 합니다.");
            } else {
                break;
            }
        }

        // 선택 과목 입력
        List<Subject> choiceSubjects = getSubjectsByType(SUBJECT_TYPE_CHOICE);
        List<Subject> selectedChoiceSubjects = new ArrayList<>();
        while (selectedChoiceSubjects.size() < 2) {
            System.out.println("선택 과목(디자인 패턴, Spring Security, Redis, MongoDB)을 입력해주세요.");
            String[] inputSubjects = sc.nextLine().split(",");

            selectedChoiceSubjects = getValidSubjects(choiceSubjects, inputSubjects);

            // 필수 과목 3개 이상 입력했는지 확인
            if (selectedChoiceSubjects.size() < 2) {
                System.out.println("선택 과목은 최소 2개 이상 선택해야 합니다.");
            } else {
                break;
            }
        }

        // 수강생 인스턴스 생성 및 저장
        System.out.println();
        Student student = new Student(sequence(INDEX_TYPE_STUDENT), studentName, selectedChoiceSubjects, selectedMandatorySubjects);
        studentStore.add(student);
        System.out.println("수강생 등록 성공!\n");
    }

    // 유효한 과목 리스트를 반환하는 메서드
    private static List<Subject> getValidSubjects(List<Subject> availableSubjects, String[] inputSubjects) {
        List<Subject> validSubjects = new ArrayList<>();
        for (String input : inputSubjects) {
            input = input.trim();
            for (Subject subject : availableSubjects) {
                if (subject.getSubjectName().equalsIgnoreCase(input)) {
                    validSubjects.add(subject);
                    break;
                }
            }
        }
        return validSubjects;
    }

    // 주어진 타입의 과목 리스트를 반환하는 메서드
    private static List<Subject> getSubjectsByType(String type) {
        List<Subject> subjects = new ArrayList<>();
        for (Subject subject : subjectStore) {
            if (subject.getSubjectType().equalsIgnoreCase(type)) {
                subjects.add(subject);
            }
        }
        return subjects;
    }

    // 수강생 목록 조회
    private static void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        // 수강생 목록이 비어있는지 확인
        if (studentStore.isEmpty()) {
            System.out.println("등록된 수강생이 없습니다.");
            return;
        }
        // 수강생 목록 출력
        for (Student student : studentStore) {
            System.out.println(student); // Student 클래스의 toString() 메서드 사용
        }
        System.out.println("\n수강생 목록 조회 성공!");
    }

    private static void displayScoreView() {
        boolean flag = true;
        while (flag) {
            System.out.println("==================================");
            System.out.println("점수 관리 실행 중...");
            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
            System.out.println("2. 수강생의 과목별 회차 점수 수정");
            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("4. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case 2 -> updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
                case 3 -> inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
                case 4 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    private static String getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        return sc.next();
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private static void createScore() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("등록된 수강생이 없습니다.");
            return;
        }

        System.out.print("과목 이름을 입력하세요: ");
        String subjectName = sc.next();
        Subject subject = findSubjectByName(subjectName);

        if (subject == null) {
            System.out.println("등록된 과목이 없습니다.");
            return;
        }

        System.out.print("시험 회차를 입력하세요 (1-10): ");
        int round = sc.nextInt();
        System.out.print("점수를 입력하세요 (0-100): ");
        int score = sc.nextInt();

        try {
            Score scoreEntry = findScoreByStudentAndSubject(studentId, subject.getSubjectId());
            if (scoreEntry == null) {
                scoreEntry = new Score(sequence(INDEX_TYPE_SCORE), studentId, subject.getSubjectId());
                scoreStore.add(scoreEntry);
            }
            scoreEntry.addScore(round, score);
            System.out.println("점수 등록 성공!");
        } catch (ScoreException e) {
            System.out.println("점수 등록 실패: " + e.getMessage());
        }
    }

    // 수강생 조회
    private static Student findStudentById(String studentId) {
        for (Student student : studentStore) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    // 과목 조회
    private static Subject findSubjectByName(String subjectName) {
        for (Subject subject : subjectStore) {
            if (subject.getSubjectName().equalsIgnoreCase(subjectName)) {
                return subject;
            }
        }
        return null;
    }

    // 점수 조회
    private static Score findScoreByStudentAndSubject(String studentId, String subjectId) {
        for (Score score : scoreStore) {
            if (score.getStudentId().equals(studentId) && score.getSubjectId().equals(subjectId)) {
                return score;
            }
        }
        return null;
    }


    // 수강생의 과목별 회차 점수 수정
    private static void updateRoundScoreBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (수정할 과목 및 회차, 점수)
        System.out.println("시험 점수를 수정합니다...");
        // 기능 구현
        System.out.println("\n점수 수정 성공!");
    }

    // 수강생의 특정 과목 회차별 등급 조회

    private static void inquireRoundGradeBySubject() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("등록된 수강생이 없습니다.");
            return;
        }

        // 수강생의 과목 조회
        getSubjects(studentId);
        System.out.println();

        System.out.print("과목 이름을 입력하세요: ");
        String subjectName = sc.next();
        Subject subject = findSubjectByName(subjectName);
        if (subject == null) {
            System.out.println("등록된 과목이 없습니다.");
            return;
        }

        // 수강생의 회차 및 등급 조회
        if(getGrades(studentId,subjectName)!=null){
            getGrades(studentId,subjectName).forEach((round,grade)->{
                System.out.println(round+"회차| "+grade);
            });
        }else{
            System.out.println("등록된 회차 별 등급이 없습니다");
            return;
        }

        // 기능 구현 (조회할 특정 과목)
        System.out.println("회차별 등급을 조회합니다...");
        // 기능 구현
        System.out.println("\n등급 조회 성공!");
    }
    private static void getSubjects(String studentId){
        for(Student student:studentStore){
            if(student.getStudentId().equals(studentId)){
                System.out.println(student);
            }
        }
    }

    //과목이름 -> 과목 아이디 반환
    private static String getSubjectId(String subjectName){
        for(Subject subject:subjectStore){
            if(subject.getSubjectName().equals(subjectName)){
                return subject.getSubjectId();
            }
        }
        return null;
    }

    //회차별 등급 반환
    private static Map<Integer,String> getGrades(String studentId, String subjectName){
        for(Score score:scoreStore){
            if(score.getStudentId().equals(studentId)&&score.getSubjectId().equals(getSubjectId(subjectName))){
                return score.getGrades();
            }
        }
        return null;
    }

}