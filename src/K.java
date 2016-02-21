import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * Klienten K
 *
 * Tilbyr bruker en enkel meny med mulighet til å be om,
 * legge til og trekke fra et nummer. Kan også hente historien
 * med oversikt over hvilke request det er gjort til tjeneren T.
 * Til slutt kan man stoppe tjeneren og klienten.
 *
 * @author Jarand Homleid Haugen og Ferdinand Forgaard
 * @version 1.0
 */
public class K {
    private static Scanner input;
    private static Socket clientSocket;
    private static BufferedReader in;
    private static DataOutputStream out;

    public static void main(String[] args) throws Exception {
        menu();
    }

    /**
     * Lager en enkel tekstbasert flervalgsmeny.
     */
    public static void menu() {
        boolean run = true;
        int valg = 0;
        input = new Scanner(System.in);

        while (run) {
            System.out.println();
            System.out.println("Klient - Meny:");
            System.out.println("--------------");
            System.out.println("1) Be om nummer");
            System.out.println("2) Legg til nummer");
            System.out.println("3) Trekk fra nummer");
            System.out.println("4) Hent historie");
            System.out.println("5) Stopp tjeneren");
            System.out.println("6) Stopp klienten");

            valg = input.nextInt();

            switch (valg) {
                case 1:
                    handshake();
                    getNumber();
                    break;
                case 2:
                    handshake();
                    addToNumber();
                    break;
                case 3:
                    handshake();
                    subFromNumber();
                    break;
                case 4:
                    handshake();
                    getHistory();
                    break;
                case 5:
                    handshake();
                    killServer();
                    break;
                case 6:
                    closeConnection();
                    run = false;
                    break;
            }

            closeConnection();

        }
    }

    /**
     * Kobler til tjeneren T via clientSocket.
     * Setter opp out- og inputstream med clientSocket.
     */
    public static void handshake() {
        try {
            clientSocket = new Socket("127.0.0.1", 7001);
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sender en forespørsel til tjeneren T hvor en ber om verdien til V.
     */
    public static void getNumber() {
        try {
            out.writeBytes("GET\n");
            System.out.println("Retur: " + in.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sender en forespørsel til tjeneren T hvor en ber om å legge til
     * et nummer til verdien V.
     */
    public static void addToNumber() {
        System.out.print("Enter number to add: ");
        int num = input.nextInt();
        try {
            out.writeBytes("ADD " + num + "\n");
            System.out.println("Retur: " + in.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sender en forespørsel til tjeneren T hvor en ber om å trekke fra
     * et nummer fra verdien V.
     */
    public static void subFromNumber() {
        System.out.print("Enter number to subtract: ");
        int num = input.nextInt();
        try {
            out.writeBytes("SUB " + num + "\n");
            System.out.println("Retur: " + in.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sender en forespørsel til tjeneren T hvor en ber om historien
     * til tjeneren, og skriver dette ut hos klienten.
     */
    public static void getHistory() {
        try {
            out.writeBytes("HISTORY\n");
            String historyReturn = in.readLine();
            System.out.println("History: ");
            String[] historyTab = historyReturn.split("NEW_LINE");
            for (String s : historyTab) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sender en forespørsel til tjeneren T hvor en ber om å
     * avslutte tjeneren.
     */
    public static void killServer() {
        try {
            out.writeBytes("KILL\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lukker clientSocket, input- og outputstream.
     */
    public static void closeConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
