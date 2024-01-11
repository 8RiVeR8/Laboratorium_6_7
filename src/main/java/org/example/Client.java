package org.example;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Client {
    static private JComboBox<Room> rooms;
    static private JPanel buttons;
    static private JLabel labelWins, labelDraws, labelLosses;
    static IServer server;
    static User myUser;
    public static void main(String[] args) {
        try {
            server = (IServer) Naming.lookup("rmi://localhost:1099/Server");
            server.connect("Connected");
            myUser = server.logIN();
        }catch (Exception e){
            System.out.println(e);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setTitle("Tic Tac Toe");
                frame.setSize(450, 600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

                // Przerwa
                frame.add(Box.createVerticalStrut(25));

                // Napis "Tic Tac Toe"
                JLabel titleLabel = new JLabel("Tic Tac Toe");
                titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Ustawienia wielkości
                frame.add(titleLabel);

                // Przerwa
                frame.add(Box.createVerticalStrut(25));

                // Combobox z marginesem
                try {
                    comboBoxWorker();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                rooms.setMaximumSize(new Dimension(150, 30)); // Ustawienia szerokości i wysokości
                rooms.setAlignmentX(Component.CENTER_ALIGNMENT);
                rooms.setBorder(new EmptyBorder(5, 2, 5, 2)); // Dodanie marginesów
                frame.add(rooms);

                // Przerwa
                frame.add(Box.createVerticalStrut(10));

                // Siatka kwadratowych buttonów 3x3
                buttons = new JPanel(new GridLayout(3, 3));
                buttons.setBorder(new EmptyBorder(10, 10, 10, 10)); // Dodanie marginesów
                for (int i = 1; i <= 9; i++) {
                    JButton button = new JButton("Button " + i);
                    button.setPreferredSize(new Dimension(80, 80)); // Ustawienia wielkości
                    buttons.add(button);
                }
                frame.add(buttons);

                // Przerwa
                frame.add(Box.createVerticalStrut(5));

                // JLabele
                labelWins = new JLabel("Wins: 0");
                labelDraws = new JLabel("Draws: 0");
                labelLosses = new JLabel("Losses: 0");

                labelWins.setFont(new Font("Arial", Font.PLAIN, 16)); // Ustawienia czcionki
                labelDraws.setFont(new Font("Arial", Font.PLAIN, 16)); // Ustawienia czcionki
                labelLosses.setFont(new Font("Arial", Font.PLAIN, 16)); // Ustawienia czcionki

                JPanel labelsPanel = new JPanel();
                labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.X_AXIS));
                labelsPanel.add(labelWins);
                labelsPanel.add(Box.createHorizontalStrut(10)); // Dodanie przerwy
                labelsPanel.add(labelDraws);
                labelsPanel.add(Box.createHorizontalStrut(10)); // Dodanie przerwy
                labelsPanel.add(labelLosses);
                frame.add(labelsPanel);

                // Przerwa
                frame.add(Box.createVerticalStrut(10));

                // Buttony
                JButton joinButton = new JButton("  Join Room  ");
                JButton createButton = new JButton("Create Room");
                JButton observeButton = new JButton("Observe Room");

                JPanel buttonsPanel2 = new JPanel();
                buttonsPanel2.setLayout(new BoxLayout(buttonsPanel2, BoxLayout.X_AXIS));
                buttonsPanel2.add(joinButton);
                buttonsPanel2.add(Box.createHorizontalStrut(20));
                buttonsPanel2.add(createButton);
                buttonsPanel2.add(Box.createHorizontalStrut(20));
                buttonsPanel2.add(observeButton);
                frame.add(buttonsPanel2);

                joinButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        join();
                    }
                });

                createButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            create();
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });

                observeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        observe();
                    }
                });

                // Przerwa
                frame.add(Box.createVerticalStrut(10));

                frame.setVisible(true);
            }
        });
    }

    public static void join() {

    }

    public static void create() throws RemoteException {
        server.createRoom(myUser);
        comboBoxWorker();
    }

    public static void observe() {

    }

    static void comboBoxWorker() throws RemoteException {
        rooms = new JComboBox<>(server.getRoomList().toArray(new Room[0]));
        rooms.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Room) {
                    value = "Room " + ((Room) value).roomID;
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
    }
}
