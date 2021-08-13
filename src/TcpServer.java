import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class TcpServer {

    private final int port; // initialize in constructor
    private boolean stopServer;
    private ThreadPoolExecutor threadPool; // handle each client in a separate thread
    private IHandler requestHandler; // what is the type of clients' tasks

    public TcpServer(int port){
        this.port = port;
        this.threadPool = null;
        stopServer = false;
    }

    public void supportClients() throws IOException, ClassNotFoundException {

        this.requestHandler = new QuestionsIHandler();


        Runnable mainServerLogic = () -> {
            this.threadPool = new ThreadPoolExecutor(3, 5,
                    10, TimeUnit.SECONDS, new LinkedBlockingQueue());



            try {
                ServerSocket  serverSocket = new ServerSocket(this.port);


            while (!stopServer) {
                System.out.println("waiting for Client");
                Socket ClientConnction = serverSocket.accept();
                System.out.println("Client Success : " + ClientConnction);

                Runnable clientHandling = () -> {
                    try {
                        requestHandler.handle(ClientConnction.getInputStream(), ClientConnction.getOutputStream());
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }


                    try {
                        ClientConnction.getInputStream().close();
                        ClientConnction.getOutputStream().close();
                        ClientConnction.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                };
                threadPool.execute(clientHandling);
            }
            serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        new Thread(mainServerLogic).start();

    }

    public void stop(){
        if(!stopServer){
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stopServer = true;
            threadPool.shutdown();
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        TcpServer webServer = new TcpServer(8010);

        webServer.supportClients();
    }
}