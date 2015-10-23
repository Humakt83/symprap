package fi.ukkosnetti.symprap.dto;

import java.io.Serializable;

public class AnswerGet implements Serializable, Comparable<AnswerGet> {

    public final String answer;

    public final Long created;

    public final Long questionId;

    public AnswerGet(String answer, Long created, Long questionId) {
        this.answer = answer;
        this.created = created;
        this.questionId = questionId;
    }

    public AnswerGet() {
        this(null, null, null);
    }

    @Override
    public int compareTo(AnswerGet another) {
        return another.created > this.created ? 1 : another.created < this.created ? -1 : 0;
    }
}
