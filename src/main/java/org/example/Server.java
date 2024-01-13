package org.example;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements Runnable, IServer{
    static ArrayList<User> userList = new ArrayList<>();
    static ArrayList<Room> roomList = new ArrayList<>();
    static Room currentRoom;

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    protected Server() throws RemoteException {
        super ();
    }

    public static void main(String[] args) {
        try {
            IServer server = new Server();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost:1099/Server", server);
            System.err.println("Server ready!");

            ServerSocket serverSocket = new ServerSocket(1098);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStream.writeObject(roomList);

            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void connect(String txt) throws RemoteException {
        System.out.println(txt);
    }

    @Override
    public User logIN() throws RemoteException {
        User user = new User(userList.size());
        userList.add(user);
        return user;
    }

    @Override
    public ArrayList<Room> getRoomList() throws RemoteException {
        return roomList;
    }

    @Override
    public void createRoom(User user) throws RemoteException {

        remove(user);

        Room room = new Room(String.valueOf(roomList.size()+1));
        room.users.add(user);
        roomList.add(room);
    }

    public void remove(User user)   {
        roomList.forEach(Room -> Room.users.removeIf(User -> User.ID == user.ID));
    }

    @Override
    public void joinRoom(User user, String selectedRoom) throws RemoteException {

        remove(user);

        Room joinedRoom = roomList.stream().filter(Room -> Room.roomID.equals(selectedRoom)).findFirst().orElse(null);

        if (joinedRoom != null && joinedRoom.users.size() < 2) {
            joinedRoom.users.add(user);
            joinedRoom.setBoard(new char[][]{{' ', ' ',' '}, {' ', ' ',' '}, {' ', ' ',' '}});

            if (joinedRoom.users.size() == 2){
                Server TicTacToe = new Server();
                TicTacToe.setCurrentRoom(joinedRoom);
                Thread thread = new Thread(TicTacToe);
                thread.start();
            }

        }
    }

    @Override
    public void run() {


    }

    private static boolean isRunning (){
        boolean ifWin = isWin();
        if (!ifWin)
            return isDraw();
        return ifWin;
    }

    private static boolean isWin(){
        char[][] board = currentRoom.board;
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == playerOne && board[i][1] == playerOne && board[i][2] == playerOne) ||   //vertical
                    (board[0][i] == playerOne && board[1][i] == playerOne && board[2][i] == playerOne)) { //horizontal
                currentRoom.setWhoWon(playerOne);
                return true;
            }

            if ((board[i][0] == playerTwo && board[i][1] == playerTwo && board[i][2] == playerTwo) ||   //vertical
                    (board[0][i] == playerTwo && board[1][i] == playerTwo && board[2][i] == playerTwo)) { //horizontal
                currentRoom.setWhoWon(playerTwo);
                return true;
            }

        }

        if ((board[0][0] == playerOne && board[1][1] == playerOne && board[2][2] == playerOne) ||   //cross
                (board[0][2] == playerOne && board[1][1] == playerOne && board[2][0] == playerOne)) {
            currentRoom.setWhoWon(playerOne);
            return true;
        }

        if ((board[0][0] == playerTwo && board[1][1] == playerTwo && board[2][2] == playerTwo) ||   //cross
                (board[0][2] == playerTwo && board[1][1] == playerTwo && board[2][0] == playerTwo)) {
            currentRoom.setWhoWon(playerTwo);
            return true;
        }

        return false;
    }

    private static boolean isDraw(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentRoom.board[i][j] == ' ') {
                    return false;
                }
            }
        }
        currentRoom.setWhoWon(' ');
        return true;
    }
}