
public interface ISubscriber {
	public void processRequest (String request) throws JMSException;
}