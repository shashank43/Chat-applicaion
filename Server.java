package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame implements ActionListener {

    //Swing
    JPanel topPanel;
    JTextField txtMsg;
    JButton btnSend;
    static JTextArea txtArea;

    //Socket Programming
    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;

    boolean typing;

    Server(){
        //top panel
        topPanel = new JPanel();
        topPanel.setLayout(null);
        Color colorTopTab = new Color(7, 94, 84);
        topPanel.setBackground(colorTopTab);
        topPanel.setBounds(0,0,450,70);
        add(topPanel);

        //return icon
        ImageIcon imgIconReturn = new ImageIcon(ClassLoader.getSystemResource("com/company/icons/3.png"));
        Image imgReturn = imgIconReturn.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon imgIconReturn2 = new ImageIcon(imgReturn);
        JLabel labelReturnIcon = new JLabel(imgIconReturn2);
        labelReturnIcon.setBounds(5, 15,30,30);
        topPanel.add(labelReturnIcon);

        //exit application using return icon
        labelReturnIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        //Profile picture
        ImageIcon imgIconProfilePic = new ImageIcon(ClassLoader.getSystemResource("com/company/icons/reinar.png"));
        Image imgProfilePic = imgIconProfilePic.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        ImageIcon imgIconProfilePic2 = new ImageIcon(imgProfilePic);
        JLabel labelProfilePic = new JLabel(imgIconProfilePic2);
        labelProfilePic.setBounds(50, 5,60,60);
        topPanel.add(labelProfilePic);

        //3 dots
        ImageIcon imgIconThreeDots = new ImageIcon(ClassLoader.getSystemResource("com/company/icons/3icon.png"));
        Image imgThreeDots = imgIconThreeDots.getImage().getScaledInstance(12, 30, Image.SCALE_DEFAULT);
        ImageIcon imgIconThreeDots2 = new ImageIcon(imgThreeDots);
        JLabel labelThreeDots = new JLabel(imgIconThreeDots2);
        labelThreeDots.setBounds(410, 20, 12, 30);
        topPanel.add(labelThreeDots);

        //Phone icon
        ImageIcon imgIconPhone = new ImageIcon(ClassLoader.getSystemResource("com/company/icons/phone.png"));
        Image imgPhone = imgIconPhone.getImage().getScaledInstance(35,35,Image.SCALE_DEFAULT);
        ImageIcon imgIconPhone2 = new ImageIcon(imgPhone);
        JLabel labelPhone = new JLabel(imgIconPhone2);
        labelPhone.setBounds(360, 20, 35, 35);
        topPanel.add(labelPhone);

        //Video call icon
        ImageIcon imgIconVideo = new ImageIcon(ClassLoader.getSystemResource("com/company/icons/video.png"));
        Image imgVideo = imgIconVideo.getImage().getScaledInstance(35,35,Image.SCALE_DEFAULT);
        ImageIcon imgIconVideo2 = new ImageIcon(imgVideo);
        JLabel labelVideo = new JLabel(imgIconVideo2);
        labelVideo.setBounds(310, 20, 35, 35);
        topPanel.add(labelVideo);

        //User name label
        JLabel labelUserName = new JLabel("Reinar Brown");
        labelUserName.setForeground(Color.white);
        labelUserName.setFont(new Font("SAN_SERIF", Font.BOLD, 24));
        labelUserName.setBounds(125, 20, 180, 20);
        topPanel.add(labelUserName);

        //status (online)
        JLabel labelStatus = new JLabel("Online");
        labelStatus.setForeground(Color.white);
        labelStatus.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        labelStatus.setBounds(125,40, 100,20);
        topPanel.add(labelStatus);

        //changing status to typing...
        Timer timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!typing){
                    labelStatus.setText("Online");
                }
            }
        });

        //set delay of 1 sec
        timer.setInitialDelay(1000);

        //Text area
        txtArea = new JTextArea();
        txtArea.setEditable(false);
        txtArea.setBounds(10, 80, 430, 510);
        txtArea.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);
        add(txtArea);

        //text message field
        txtMsg = new JTextField();
        txtMsg.setBounds(5, 600, 350, 40);
        txtMsg.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        add(txtMsg);

        //detect if user is typing something
        txtMsg.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                labelStatus.setText("Typing...");
                timer.stop();
                typing = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                typing = false;
                if(!timer.isRunning()){
                    timer.start();
                }
            }
        });

        //Send button
        btnSend = new JButton("Send");
        btnSend.setBounds(360, 600, 85, 40);
        btnSend.setBackground(new Color(7, 94, 84));
        btnSend.setForeground(Color.white);
        btnSend.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        btnSend.addActionListener(this);
        add(btnSend);

        //GUI
        setLayout(null);
        setSize(450, 650);
        setLocation(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String msgOut = txtMsg.getText();
            txtArea.setText(txtArea.getText() + '\n' + "\t\t\t" + msgOut);
            txtMsg.setText("");

            dataOutputStream.writeUTF(msgOut);
        }
        catch (Exception exception){

        }
    }

    public static void main(String[] args) {
        new Server();

        try {
            serverSocket = new ServerSocket(5000);
            while(true) {
                socket = serverSocket.accept();

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                while(true) {
                    String msgInput = "";
                    msgInput = dataInputStream.readUTF();
                    txtArea.setText(txtArea.getText() + '\n' + msgInput);
                }
            }
            //serverSocket.close();
            //socket.close();
        }
        catch (Exception exception){

        }
    }
}
