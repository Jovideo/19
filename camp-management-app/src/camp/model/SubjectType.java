package camp.model;

public class SubjectType {
    public static final SubjectType MANDATORY = new SubjectType("필수과목");
    public static final SubjectType CHOICE = new SubjectType("선택과목");

    private final String description;

    SubjectType(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public String toString(){
        return description;
    }
}
