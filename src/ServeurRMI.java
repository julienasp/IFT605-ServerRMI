import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
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
		if (System.getSecurityManager() == null) {			
			System.setSecurityManager(new SecurityManager());			
		}
		try {
			String stubName = "ServeurRMI";
			AdminToken at = new AdminToken("rtCCTYgeUV&aP5w?");
			ServeurRMI mServeur = new ServeurRMI(at);		
			
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
		        	  // Pour TEST EN LIVE
		        	  //Thread.currentThread();
		        	  //Thread.sleep(60000);
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
		} catch (CancellationException ce){
			//Nothing to show
			System.out.println("Serveur: le resultat de .get() est indisponible, car la future qui s'y rattache a été annulée.");
			return -1;
		}catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return -1;
		}
	}

	public void interruptThread(AdminToken at, int s) throws RemoteException {
		// TODO Auto-generated method stub
		if(this.at.isPrivateKeyOK(at)){
			if(vFuture.size() > s){
				try{
					vFuture.get(s).cancel(true); 
				}catch(Exception e){
					//Nothing to show
				}finally{
					System.out.println("Serveur: le thread à bien été arrêter");
				}	
			}
			else System.out.println("Serveur: le AdminToken reçu du client est valide. Par contre, le thread n'existe plus ou bien l'index est mauvais.");
		}
		else System.out.println("Serveur: le AdminToken reçu du client est invalide. La méthode ne sera pas exécuté.");		
	}
}
