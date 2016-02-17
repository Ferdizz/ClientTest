import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Ferdinand on 17.02.2016.
 */
public class Client {

    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);

        String melding = "Hello World!";
        String nyMelding = "";

        Socket clientSocket = new Socket("127.0.0.1", 7001);
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        out.writeBytes(melding + "\n");
        nyMelding = in.readLine();

        System.out.println("Melding fra tjener: " + nyMelding);
        clientSocket.close();

    }

    public void menu(Scanner input){

        boolean run = true;
        int valg = 0;

        while(run){

            System.out.println("Klient - Meny:");
            System.out.println("--------------");
            System.out.println("1) Send \"Hello World!\"" + "\n2) Send ett tall (5)"
            + "\n3) Avslutt server" + "\n0) Avslutt klient");

            valg = input.nextInt();

            switch (valg){
                case 1:
                    sendMsg();
                    break;
                case 2:
                    sendInt();
                    break;
                case 3:
                    exitServer();
                    break;
                case 0:
                    System.exit(0);
                    break;
            }

        }


    }

    public void sendMsg(){

    }

    public void sendInt(){

    }


}
