package ekindergarten.model;

public enum ProgressGrade {
    YES ("Tak"),
    NO ("Nie"),
    SOMETIMES("Czasami");

    private final String description;

    ProgressGrade(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
