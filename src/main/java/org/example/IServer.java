package org.example;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IServer extends Remote {
    void connect (String txt) throws RemoteException;
    User logIN () throws RemoteException;
    ArrayList<Room> getRoomList () throws RemoteException;
    void createRoom (User user) throws RemoteException;
    void joinRoom (User user, String selectedRoom) throws RemoteException;
}