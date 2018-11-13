package ekindergarten.model;

public enum ProgressCategory {
    PHYSICAL("Fizyczny"),
    SOCIAL_AND_MORAL("Moralno-spoleczny"),
    MENTAL("Mentalny");

    private final String description;

    ProgressCategory(String description) {
        this.description = description;
    }

    @Override
    public String toString() { return description; }
}

