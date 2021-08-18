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
        String[] options1 ={"Tel-aviv","Paris","Washington DC","Helsinki"};
        String[] options2 ={"London","Roma","Paris","Washington DC"};
        String[] options3 ={"Tel-aviv","Barcelona","Damascus","New-York"};
        String[] options4 ={"Roma","London","Torronto","Sydney"};
        String[] options5 ={"Kaboul","London","Bucarest","Budapest"};

        questions[0]=new Questions("What is the capital of France",options1,1);
        questions[1]=new Questions("What is the capital of USA",options2,3);
        questions[2]=new Questions("What is the capital of Spain",options3,1);
        questions[3]=new Questions("What is the capital of Italy",options4,0);
        questions[4]=new Questions("What is the capital of England",options5,1);

    }


}

