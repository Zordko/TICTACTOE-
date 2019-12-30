
public interface Subject {

	public void addClient(Observer o);
	public void removeClient(Observer o);
	public void notifyClients();
}
