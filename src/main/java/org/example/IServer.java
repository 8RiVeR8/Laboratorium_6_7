package org.example;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IServer extends Remote {
    void Connect (String txt) throws RemoteException;
     User logIN () throws RemoteException;
     ArrayList<Room> getRoomList () throws RemoteException;
}
