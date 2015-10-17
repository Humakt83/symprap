package fi.ukkosnetti.symprap.dto;

import java.io.Serializable;

public class Question implements Serializable {

    public enum AnswerType {
        TEXT, DOUBLE, BOOLEAN;
    }

    public final Long id;

    public final String question;

    public final AnswerType answerType;

    public final Long symptomId;

    public Question(Long id, String question, AnswerType answerType, Long symptomId) {
        this.id = id;
        this.question = question;
        this.answerType = answerType;
        this.symptomId = symptomId;
    }

    public Question() {
        this(null,null,null,null);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answerType=" + answerType +
                ", symptomId=" + symptomId +
                '}';
    }
}
