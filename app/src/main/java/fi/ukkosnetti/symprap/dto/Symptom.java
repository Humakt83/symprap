package fi.ukkosnetti.symprap.dto;

public class Symptom {

    public final Long id;

    public final String symptom;

    public Symptom(Long id, String symptom) {
        this.id = id;
        this.symptom = symptom;
    }

    public Symptom() {
        this(null, null);
    }

    @Override
    public String toString() {
        return "Symptom{" +
                "id=" + id +
                ", symptom='" + symptom + '\'' +
                '}';
    }
}
