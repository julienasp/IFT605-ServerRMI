import java.rmi.Remote;
import java.rmi.RemoteException;
import core.AdminToken;


public interface IRemoteAdminHandler extends Remote {
	/**
	 * Kill a thread if needed.
	 *
	 * @return      y double
	 * @param       at AdminToken, t String         
	 */
	public void interruptThread(AdminToken at, String t) throws RemoteException;
}
