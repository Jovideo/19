package camp.model;

public enum StudentStatus {
    // 수강생 상태 타입
    GREEN("양호"),
    YELLOW("주의"),
    RED("위험");

    private final String description;

    StudentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
