package fi.ukkosnetti.symprap.dto;

import java.io.Serializable;

public class Answer implements Serializable{

    public final Long questionId;

    public final Long userId;

    public final String answer;

    public Answer(Long questionId, Long userId, String answer) {
        this.questionId = questionId;
        this.userId = userId;
        this.answer = answer;
    }

    public Answer() {
        this(null, null, null);
    }
}
