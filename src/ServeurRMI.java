import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import core.Equation;


public class ServeurRMI extends UnicastRemoteObject implements IRemoteEquation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8532723507508499007L;

	protected ServeurRMI() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		try {			
			ServeurRMI mServeur = new ServeurRMI();			
			String url = "rmi://localhost/ServeurRMI";
			Naming.rebind(url, mServeur);
			System.out.println("Enregistrement de l'objet avec l'url : " + url);			
			
			System.out.println("Serveur lancé");
		} catch (Exception e) {			
			System.out.println(e.getMessage());			
		}
	}

	@Override
	public double getEquationValue(Equation e, double v) throws RemoteException{
		double t = e.getFunctionValue(v);
		System.out.println("Serveur: la valeur de l'équation pour x=" + Double.toString(v) + " est: " + Double.toString(t));	
		return t;		
	}

}
