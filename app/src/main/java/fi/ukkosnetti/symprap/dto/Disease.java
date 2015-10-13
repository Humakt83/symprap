package fi.ukkosnetti.symprap.dto;

public class Disease {

    public final Long id;

    public final String disease;

    public Disease(Long id, String disease) {
        this.id = id;
        this.disease = disease;
    }

    public Disease() {
        this(null, null);
    }

    @Override
    public String toString() {
        return "Disease{" +
                "id=" + id +
                ", disease='" + disease + '\'' +
                '}';
    }
}
