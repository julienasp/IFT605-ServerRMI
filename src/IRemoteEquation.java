
import java.rmi.Remote;
import java.rmi.RemoteException;

import core.Equation;



public interface IRemoteEquation extends Remote {
	/**
	 * Computes de value of y (the Equation result) given the value of s
	 *
	 * @return      y double
	 * @param       s double         
	 */
	public double getEquationValue(Equation e, double v) throws RemoteException;

}
