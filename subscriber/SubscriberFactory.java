
public abstract class SubscriberFactory {
	public abstract ISubscriber getSubscriber (String type);
	public abstract void addSubscriberMap (String name, ISubscriber subscriber);
}