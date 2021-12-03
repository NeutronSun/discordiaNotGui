 import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.StringBuilder;

public class Server {
    public static void main(String[] args) throws Exception {
        int portNumber = 11700;
        ServerSocket serverSocket = new ServerSocket(portNumber);
        FileManager fm = new FileManager();
        System.out.println("ip: " + Inet4Address.getLocalHost().getHostAddress());
        System.out.println("port: " + portNumber);
        System.out.println("Waiting for user..." +  new StringBuilder().appendCodePoint(0x1F920).toString());
        MessageBox mailBox = new MessageBox();
        UserManager um = new UserManager();
        int contThread = 0;
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ServerThread(clientSocket, mailBox, um, fm, contThread)).start();
            System.out.println("Connection Accepted with client-" + contThread);
            contThread++;
        }
    }

}
