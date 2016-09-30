
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import core.AdminToken;
import core.Equation;


public class ServeurRMI implements IRemoteEquation, IRemoteAdminHandler {
	private AdminToken at = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8532723507508499007L;

	protected ServeurRMI() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}	

	protected ServeurRMI(AdminToken at) throws RemoteException {
		super();
		this.at = at;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		try {
			String stubName = "ServeurRMI";
			AdminToken at = new AdminToken("rtCCTYgeUV&aP5w?");
			ServeurRMI mServeur = new ServeurRMI(at);			
			/*String url = "rmi://localhost/" + stubName;
			Naming.rebind(url, mServeur);
			System.out.println("Enregistrement de l'objet avec l'url : " + url);*/
			
			
			 	IRemoteEquation stub = (IRemoteEquation) UnicastRemoteObject.exportObject(mServeur, 0);
				Registry registry = LocateRegistry.getRegistry("localhost");
            	registry.rebind(stubName, stub);	 
			
			System.out.println("Enregistrement de l'objet avec le nom suivant : " + stubName);
			System.out.println("Serveur lancé...");
			
		} catch (Exception e) {			
			System.out.println(e.getMessage());			
		}
	}

	public double getEquationValue(Equation e, double v) throws RemoteException{
		double t = e.getFunctionValue(v);
		System.out.println("Serveur: la valeur de l'équation pour x=" + Double.toString(v) + " est: " + Double.toString(t));	
		System.out.println(Thread.getAllStackTraces().toString());
		return t;		
	}

	public void interruptThread(AdminToken at, String t) throws RemoteException {
		// TODO Auto-generated method stub
		if(this.at.isPrivateKeyOK(at)){			
			
		}
		System.out.println("Serveur: le AdminToken reçu est invalide.");		
	}
}
