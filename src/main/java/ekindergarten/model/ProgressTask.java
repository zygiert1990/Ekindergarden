package ekindergarten.model;

public enum ProgressTask {
    PHYSICAL_1("Wykonuje czynności związane z samoobsługą"),
    PHYSICAL_2("Samodzielnie ubiera się"),
    PHYSICAL_3("Prawidłowo trzyma narzędzie do rysowania"),
    PHYSICAL_4("Wycina nożyczkami po linii prostej"),
    PHYSICAL_5("Wykonuje prace plastyczne"),
    MENTAL_1("Opowiada co się dzieje na obrazku"),
    MENTAL_2("Wypowiada się spontanicznie"),
    MENTAL_3("Tworzy prace własne i wg wzoru"),
    MENTAL_4("Rysuje postać ludzką"),
    MENTAL_5("Skupia się na zadaniu, doprowadza pracę do końca"),
    MORAL_1("Dba o wspólne zabawki i sprzęty"),
    MORAL_2("Współdziałą w zespole"),
    MORAL_3("Wie, w jakim mieszka mieście i na jakiej ulicy"),
    MORAL_4("Wie, w jakim mieszka kraju i co jest jego stolicą"),
    MORAL_5("Zna godło i barwy narodowe");

    private final String description;

    ProgressTask(String description) {
        this.description = description;
    }

    @Override
    public String toString() { return description; }
}
