package camp;

import camp.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class CampManagementApplication {
    // 데이터 저장소
    private static List<Student> studentStore = new ArrayList<>();
    private static List<Subject> subjectStore = new ArrayList<>();
    private static List<Score> scoreStore = new ArrayList<>();

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
        subjectStore = new ArrayList<>(Arrays.asList(
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Java",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "객체지향",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Spring",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "JPA",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "MySQL",
                        SubjectType.MANDATORY
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "디자인 패턴",
                        SubjectType.CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Spring Security",
                        SubjectType.CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "Redis",
                        SubjectType.CHOICE
                ),
                new Subject(
                        sequence(INDEX_TYPE_SUBJECT),
                        "MongoDB",
                        SubjectType.CHOICE
                ))
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
            System.out.println("3. 수강생 상태 관리");
            System.out.println("4. 프로그램 종료");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> displayStudentView(); // 수강생 관리
                case 2 -> displayScoreView(); // 점수 관리
                case 3 -> displayStudentStateView(); // 수강생 상태 관리
                case 4 -> flag = false; // 프로그램 종료
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
            System.out.println("3. 수강생 삭제");
            System.out.println("4. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createStudent(); // 수강생 등록
                case 2 -> inquireStudent(); // 수강생 목록 조회
                case 3 -> deleteStudent(); // 수강생 삭제
                case 4 -> flag = false; // 메인 화면 이동
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

        sc.nextLine();

        // 수강생 이름
        String studentName = getInput("수강생 이름 입력: ");

        // 필수 과목 입력
        List<Subject> mandatorySubjects = getSelectSubjects(SubjectType.MANDATORY,3, 5);
        // 선택 과목 입력
        List<Subject> choiceSubjects = getSelectSubjects(SubjectType.CHOICE, 2, 4);

        // 수강생 인스턴스 생성 및 저장
        System.out.println();
        Student student = new Student(sequence(INDEX_TYPE_STUDENT), studentName, mandatorySubjects, choiceSubjects);
        studentStore.add(student);
        System.out.println("수강생 등록 성공!\n");
    }

    // 과목 입력 메서드
    private static List<Subject> getSelectSubjects(SubjectType type, int minRequired, int maxRequired) {
        List<Subject> availableSubjects = getSubjectsByType(type);
        List<Subject> selectedSubjects = new ArrayList<>();

        while (true){
            System.out.printf("%s(%s)을 입력해주세요.(최소 %d개, 최대 %d개)\n",type.getDescription(), String.join(", ", availableSubjects.stream().map(Subject::getSubjectName).toList()), minRequired, maxRequired);
            String[] inputSubjects = getInput("").split(",");

            selectedSubjects = getValidSubjects(availableSubjects, inputSubjects);

            if (selectedSubjects.size() < minRequired)
                System.out.printf("%s은 최소 %d개 이상 선택하야 합니다.\n", type.getDescription(), minRequired);
            else if (selectedSubjects.size() > maxRequired) {
                System.out.printf("%s은 최대 %d개까지 선택할 수 있습니다.\n", type.getDescription(), maxRequired);
            }else {
                break;
            }
        }

        return selectedSubjects;
    }



    // 유효한 과목 리스트를 반환하는 메서드(중복 처리 추가)
    private static List<Subject> getValidSubjects(List<Subject> availableSubjects, String[] inputSubjects) {
        List<Subject> validSubjects = new ArrayList<>();
        Set<String> uniqueInputs = new HashSet<>();

        for (String input : inputSubjects) {
            input = input.trim();
            if (!uniqueInputs.add(input.toLowerCase())){
                System.out.println("경고: "+input + "은(는) 중복 입력되어 한 번만 처리됩니다.");
                continue;
            }
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
    private static List<Subject> getSubjectsByType(SubjectType type) {
        return subjectStore.stream()
                .filter(subject -> subject.getSubjectType() == type)
                .collect(Collectors.toList());
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
        studentStore.forEach(System.out::println);
        System.out.println("\n수강생 목록 조회 성공!");
    }

    // 수강생 삭제
    private static void deleteStudent() {
        Student student = getStudentById();
        if (student == null){
            System.out.println("등록된 수강생이 없습니다.");
            return;
        }

        System.out.println("다음 학생을 삭제하시겠습니까?");
        System.out.println(student);
        System.out.println("삭제하려면 'Y'를 입력하세요: ");
        String confirm = sc.nextLine().trim().toUpperCase();

        if (confirm.equals("Y")){
            studentStore.remove(student);
            // 관련된 점수 정보도 삭제
            scoreStore.removeIf(score -> score.getStudentId().equals(student.getStudentId()));
            System.out.println("학생 정보가 삭제되었습니다.");
        }else
            System.out.println("삭제가 취소되었습니다.");
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

    private static void displayStudentStateView() {
        boolean flag = true;
        while (flag){
            System.out.println("==================================");
            System.out.println("수강생 상태 관리 실행 중...");
            System.out.println("1. 수강생 상태 변경");
            System.out.println("2. 수강생 상태 조회");
            System.out.println("3. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input){
                case 1 -> changeStudentStatus();
                case 2 -> inquireStudentStatus();
                case 3 -> flag = false;
                default -> {
                    System.out.println("잘못된 입력입니다.\n 메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    // 수강생 상태 변경
    private static void changeStudentStatus() {
        Student student = getStudentById();
        if (student == null){
            System.out.println("등록된 수강생이 없습니다.");
            return;
        }

        System.out.println("변경할 상태를 선택하세요:");
        System.out.println("1. GREEN (양호)");
        System.out.println("2. YELLOW (주의)");
        System.out.println("3. RED (위험)");
        int statusChoice = getIntInput("선택: ",1,3);

        StudentStatus newStatus;
        switch (statusChoice){
            case 1 -> newStatus = StudentStatus.GREEN;
            case 2 -> newStatus = StudentStatus.YELLOW;
            case 3 -> newStatus = StudentStatus.RED;
            default -> {
                System.out.println("잘못된 선택입니다.");
                return;
            }
        }

        student.setStatus(newStatus);
        System.out.println("수강생 상태가 변경되었습니다 : "+ newStatus.getDescription());
    }
    // 수강생 상태 조회
    private static void inquireStudentStatus() {
        System.out.println("\n 수강생 상태를 조회합니다...");
        if (studentStore.isEmpty()){
            System.out.println("등록된 수강생이 없습니다.");
            return;
        }
        for (Student student : studentStore){
            System.out.println(student.getStudentId() + " - " + student.getStudentName() +
                    ": " + student.getStatus().getDescription());
        }
        System.out.println("\n 수강생 상태 조회 완료!");
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private static void createScore() {
        Student student = getStudentById();
        if (student == null) {
            System.out.println("등록된 수강생이 없습니다.");
            return;
        }

        Subject subject = getSubjectByName(student);

        if (subject == null) {
            System.out.println("등록된 과목이 없습니다.");
            return;
        }

        if (!student.hasSubject(subject)){
            System.out.println("이 학생은 해당 과목을 선택하지 않았습니다. 점수를 등록할 수 없습니다.");
            return;
        }

        int round = getIntInput("시험 회차를 입력하세요 (1-10): ",1, 10);
        int score = getIntInput("점수를 입력하세요 (0-100): ",0, 100);

        try {
            Score scoreEntry = findOrCreateScore(student.getStudentId(), subject.getSubjectId());
            scoreEntry.addScore(round, score);
            System.out.println("점수 등록 성공!");
        } catch (ScoreException e) {
            System.out.println("점수 등록 실패: " + e.getMessage());
        }
    }


    // 수강생 조회
    private static Student findStudentById(String studentId) {
        return studentStore.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
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

    // 입력된 과목을 찾고 점수 공간을 생성
    private static Score findOrCreateScore(String studentId, String subjectId) {
        Score scoreEntry = findScoreByStudentAndSubject(studentId, subjectId);
        if (scoreEntry == null){
            scoreEntry = new Score(sequence(INDEX_TYPE_SCORE), studentId, subjectId);
            scoreStore.add(scoreEntry);
        }
        return scoreEntry;
    }

    // 점수 조회
    private static Score findScoreByStudentAndSubject(String studentId, String subjectId) {
        return scoreStore.stream()
                .filter(s -> s.getStudentId().equalsIgnoreCase(studentId) && s.getSubjectId().equalsIgnoreCase(subjectId))
                .findFirst()
                .orElse(null);
    }


    // 수강생의 과목별 회차 점수 수정
    private static void updateRoundScoreBySubject() {
        Student student = getStudentById();

        if (student == null){
            System.out.println("해당 학생을 찾을 수 없습니다.");
            return;
        }


        Subject subject = getSubjectByName(student);

        if (subject == null) {
            System.out.println("등록된 과목이 없습니다.");
            return;
        }

        if (!student.hasSubject(subject)){
            System.out.println("이 학생은 해당 과목을 선택하지 않았습니다. 점수를 수정할 수 없습니다.");
            return;
        }


        int round = getIntInput("수정할 회차를 입력하세요:",1,10);

        int newScore = getIntInput("새로운 점수를 입력하세요:",0,100);

        try {
            Score score = findScoreByStudentAndSubject(student.getStudentId(), subject.getSubjectId());
            if (score == null){
                System.out.println("해당 과목의 점수 정보를 찾을 수 없습니다.");
                return;
            }

            score.updateScore(round, newScore);
            System.out.println("점수가 성공적으로 수정되었습니다.");
        }catch (ScoreException e){
            System.out.println("점수 수정 중 오류 발생 : "+e.getMessage());
        }
    }

    // 수강생 고유번호으로 학생 정보 불러오기
    private static Student getStudentById() {
        sc.nextLine();
        String studentId = getInput("\n 관리할 수강생의 번호를 입력하세요."); // 관리할 수강생 고유 번호
        Student student = findStudentById(studentId);

        if (student == null)
            System.out.println("해당 학생을 찾을 수 없습니다.");
        return student;
    }
    //
    private static Subject getSubjectByName(Student student) {
        System.out.println("과목을 선택하세요:");
        displaySubjects(student);
        String subjectName = getInput("");
        Subject subject = findSubjectByName(subjectName);
        if (subject == null)
            System.out.println("해당 과목을 찾을 수 없습니다.");
        return subject;
    }

    // 수강생의 특정 과목 회차별 등급 조회
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
        // 수강생이 선택한 과목인지 확인
        if (!student.hasSubject(subject)){
            System.out.println("이 학생은 해당 과목을 선택하지 않았습니다. 점수를 등록할 수 없습니다.");
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
    //회차별 등급 반환
    private static Map<Integer,String> getGrades(String studentId, String subjectName){
        for(Score score:scoreStore){
            if(score.getStudentId().equals(studentId)&&score.getSubjectId().equals(getSubjectId(subjectName))){
                return score.getGrades();
            }
        }
        return null;
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

    // 학생이 고른 필수 과목 목록과 선택 과목 목록 보여주는 메서드
    private static void displaySubjects(Student student){
        System.out.println("\n"+"필수 과목:");
        student.getMandatorySubjects().forEach(s -> System.out.print(s.getSubjectName() + ", "));
        System.out.println("\n"+"선택 과목:");
        student.getChoiceSubjects().forEach(s -> System.out.print(s.getSubjectName() + ", "));
        System.out.println();
    }

    // 이름, 과목 입력 처리 메서드
    private static String getInput(String s) {
        System.out.print(s);
        return sc.nextLine().trim();
    }

    private static int getIntInput(String s, int min, int max) {
        while (true){
            try {
                System.out.print(s);
                int input = Integer.parseInt(sc.nextLine().trim());
                if (input >= min && input <= max)
                    return input;
                System.out.printf("입력은 %d에서 %d 사이여야 합니다.", min, max);
            }catch (NumberFormatException e){
                System.out.println("올바른 숫자를 입력해주세요.");
            }
        }
    }

}