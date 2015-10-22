package fi.ukkosnetti.symprap.dto;

import java.util.Date;

public class AnswerGet {

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
}
