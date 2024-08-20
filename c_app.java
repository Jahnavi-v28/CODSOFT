import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class c_app extends JFrame {
     JTextArea c_area;  
     JTextField input;  
     JButton send_Button;  
     PrintWriter out;  
     ServerSocket serverSocket;  
     Socket socket;  
     boolean isServer;  
    //chat application
    public c_app(boolean isServer) {
        this.isServer = isServer;  
        setTitle("Chat Server (Automated Response)");  
        setSize(400, 300);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
       
        c_area = new JTextArea();
        c_area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(c_area);
        add(scrollPane, BorderLayout.CENTER);  

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // input text field
        input = new JTextField();
        panel.add(input, BorderLayout.CENTER);  

        // send button
        send_Button = new JButton("Send");
        panel.add(send_Button, BorderLayout.EAST);  

        add(panel, BorderLayout.SOUTH);

        send_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                send_msg();  
            }
        });

        new Thread(new Runnable() {
            public void run() {
                startServer();  
            }
        }).start();
    }

    // start the server
    public void startServer() {
        try {
            serverSocket = new ServerSocket(12345);  
            c_area.append("Server started...\n");  

            socket = serverSocket.accept();  
            c_area.append("Client connected (simulated).\n");  

            out = new PrintWriter(socket.getOutputStream(), true);  


        } catch (IOException e) {
            e.printStackTrace();  
        }
    }

    // send a message 
    public void send_msg
    () {
        String message = input.getText();  
        c_area.append("You: " + message + "\n");  
        input.setText("");  

        String response = generate(message);
        c_area.append("Computer: " + response + "\n");  

        out.println(response);

        if (message.equalsIgnoreCase("bye")) {
            c_area.append("Conversation ended.\n");
            stopServer();  
        }
    }

    // Method to generate automated responses based on input messages
     String generate(String input) {
        // Rule-based responses to specific input messages
        if (input.equalsIgnoreCase("hi") || input.equalsIgnoreCase("hello")) {
            return "Hello! How can I help you today?";
        } else if (input.equalsIgnoreCase("how are you?")) {
            return "I'm a chatbot, so I don't have feelings, but thanks for asking!";
        } else if (input.equalsIgnoreCase("bye")) {
            return "Goodbye! Have a great day!";
        } else {
            return "I'm not sure how to respond to that.";  // Default response 
        }
    }

    // Method to stop the server
    public void stopServer() {
        try {
            if (out != null) out.close();  // Close the output stream
            if (socket != null) socket.close();  // Close the client socket
            if (serverSocket != null) serverSocket.close();  // Close the server socket
        } catch (IOException e) {
            e.printStackTrace();  // Handle exceptions
        }
    }

    // Main method 
    public static void main(String[] args) {
        // Start the server in the Swing Event 
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new c_app(true).setVisible(true);  
            }
        });
    }
}
