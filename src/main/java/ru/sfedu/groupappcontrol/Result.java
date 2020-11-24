package ru.sfedu.groupappcontrol;

import ru.sfedu.groupappcontrol.models.enums.Outcomes;

public class Result<T> {
    private T data;
    private String answer;
    private Outcomes status;

    public Result(Outcomes status, String answer, T data){
        this.status = status;
        this.answer = answer;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Outcomes getStatus() {
        return status;
    }

    public void setStatus(Outcomes status) {
        this.status = status;
    }
}
