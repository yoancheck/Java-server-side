import java.io.*;

public class QuestionsIHandler implements IHandler{

    private Questions[] questions = new Questions[5];
    private int questionNumber=0;
    private volatile boolean doWork = true;
    private int numberOfTrue=0;

    @Override
    public void resetMembers() {
        doWork = true;
        questionNumber=0;
        numberOfTrue=0;
    }

    @Override
    public void handle(InputStream fromClient, OutputStream toClient) throws IOException, ClassNotFoundException {
        resetMembers();
        System.out.println("Loading Dummy Data...");
        loadData();
        System.out.println("Loaded");
        ObjectInputStream objectInputStream = new ObjectInputStream(fromClient);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(toClient);

        boolean doWork = true;

        while(doWork){


            switch (objectInputStream.readObject().toString()){

                case "getQuestion":{
                    String answer;
                    objectOutputStream.writeObject(questions[questionNumber].getQuestion());
                    objectOutputStream.writeObject(questions[questionNumber].getOptions());
                    answer = objectInputStream.readObject().toString();

                    if(answer.equals(questions[questionNumber++].getTrueAnswer())){
                        numberOfTrue++;
                    }
                }break;

                case "getScore" : {  objectOutputStream.writeObject(numberOfTrue); };break;

                case "stop":{
                    doWork = false;
                    break;
                }
            }
        }


    }


    private void loadData(){
        String[] options1 ={"לונדון","ברלין","פריז","לוס אנג'לס"};
        String[] options2 ={"אנקה","תימרה","גימלה","נאקה"};
        String[] options3 ={"צנחנים","פיקוד העורף","שריון","גולני"};
        String[] options4 ={"1440","2400","1200","840"};
        String[] options5 ={"יאיר ניצני","גבי ניצן","יוסי גינסברג","גדי טאוב"};

        questions[0]=new Questions("באיזו עיר נמצאת כיכר פיקדילי",options1,0);
        questions[1]=new Questions("כיצד נקראת נקבת הגמל?",options2,3);
        questions[2]=new Questions("אם חייל מסתובב עם כומתה בצבע כתום זה כנראה אומר שהוא משרת ב...",options3,1);
        questions[3]=new Questions("כמה דקות יש ביממה?",options4,0);
        questions[4]=new Questions("מי כתב את הספר באדולינה?",options5,1);


    }


}
