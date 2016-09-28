import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IRemoteEquation extends Remote {
	/**
	 * Computes de value of y (the Equation result) given the value of s
	 *
	 * @return      y double
	 * @param       s double         
	 */
	public double getEquation(String s) throws RemoteException;

}
