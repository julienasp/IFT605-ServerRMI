
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import core.AdminToken;
import core.Equation;


public class ServeurRMI implements IRemoteEquation, IRemoteAdminHandler {
	private AdminToken at = null;
	private ExecutorService executor = Executors.newCachedThreadPool();
	private Vector<Future<Double>> vFuture = new Vector<Future<Double>>();
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

	public double getEquationValue(final Equation e, final double v) throws RemoteException{
		e.printUserReadable();
		System.out.println("Serveur: dans getEquationValue pour l'équation ci-dessus");
		Future<Double> f = executor.submit(
		      new Callable<Double>() {
		          public Double call() throws InterruptedException, ExecutionException {
		        	  e.printUserReadable();
		        	  System.out.println("Serveur: dans call() pour pour l'équation ci-dessus");
		        	  return e.getFunctionValue(v);
		          }
		      });
		
		vFuture.add(f);		
		Double t;		
		try {
			t = f.get();
			vFuture.remove(f);
			System.out.println("Serveur: la valeur de l'équation pour x=" + Double.toString(v) + " est: " + Double.toString(t));	
			return t;		
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return -1;
		}
	}

	public void interruptThread(AdminToken at, int s) throws RemoteException {
		// TODO Auto-generated method stub
		if(this.at.isPrivateKeyOK(at)){			
			vFuture.get(s).cancel(true);
		}
		System.out.println("Serveur: le AdminToken reçu est invalide.");		
	}
}
