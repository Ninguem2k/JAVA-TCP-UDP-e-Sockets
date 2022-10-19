import java.net.*;
import java.util.Scanner;
import java.io.*;

public class TCPServer {
    public static void main(String args[]) {
        try {
            // define em qual porta vai receber as informações
            int serverPort = 7999;
            // seta a porta no qual vai ser usada gerando um objeto que captura oq esta
            // sendo enviado naquela porta
            try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
                // entra em um loop infinito aguardando informação
                while (true) {
                    // System.out.println("Aguardando Cliente");
                    Socket clientSocket = serverSocket.accept();
                    // System.out.println("Cliente " + clientSocket.getInetAddress() + " se
                    // conectou");
                    Connection connection = new Connection(clientSocket);
                    // System.out.println("connection:" + connection);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro of Connection: " + e.getMessage());
        }
    }
}

class Connection extends Thread {
    DataInputStream input;
    DataOutputStream output;
    Socket clientSocket;

    public Connection(Socket NewClientSocket) {
        try {
            clientSocket = NewClientSocket;
            // pega inforção enviada para porta
            input = new DataInputStream(clientSocket.getInputStream());
            // System.out.println("input user: " + input);
            // retorna a inforção enviada para porta
            output = new DataOutputStream(clientSocket.getOutputStream());
            // System.out.println("output user: " + output);
            // inicia uma nova thread
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try (Scanner useriput = new Scanner(System.in)) {
            String msg = null;
            try {
                // pega os dados para mostrar pro server
                String data = input.readUTF();
                System.out.println("Mensagem recebida: " + data);
                // System.out.println("remetente:" + clientSocket.getInetAddress());
                System.out.print("Escreva sua Mensagem: ");
                 msg = useriput.next();
                // retorna infor para cliente
                output.writeUTF(msg);
                // System.out.println("_________________________________________________________");

            } catch (EOFException e) {
                System.out.println("EOF:" + e.getMessage());
            } catch (IOException e) {
                System.out.println("readline:" + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    /* close failed */}
            }
        }

    }
}
