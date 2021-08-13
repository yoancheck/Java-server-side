import java.io.Serializable;

public class Questions implements Serializable {


    private String Question ="";
    private String[] options = new String[4];
    private int trueAnswer;

    public Questions(String question, String[] options, int trueAnswer){
        this.Question=question;
        this.options=options;
        this.trueAnswer=trueAnswer;

    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getTrueAnswer()
    {

        return options[trueAnswer];
    }

    public void setTrueAnswer(int trueAnswer) {
        this.trueAnswer = trueAnswer;
    }
}
