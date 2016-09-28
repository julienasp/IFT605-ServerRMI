import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ServeurRMI extends UnicastRemoteObject implements IRemoteEquation {

	protected ServeurRMI() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getEquation(String s) throws RemoteException{
		// TODO Auto-generated method stub
		return 0;
	}

}
