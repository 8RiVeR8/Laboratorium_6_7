package org.example;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements IServer{
    ArrayList<User> userList = new ArrayList<>();
    ArrayList<Room> roomList = new ArrayList<>();
    protected Server() throws RemoteException {
        super ();
    }

    public static void main(String[] args) {
        try{
            IServer server = new Server();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost:1099/Server", server);
            System.err.println("Server ready!");
        }catch (Exception e){
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
        Room room = new Room(String.valueOf(roomList.size()+1));
        room.users.add(user);
        roomList.add(room);
    }
}