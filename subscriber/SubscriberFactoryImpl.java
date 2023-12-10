
@Component("subscriberFactory")
public class SubscriberFactoryImpl extends com.ms.datawise.distn.subscriber.SubscriberFactory {
	final private Map<String, ISubscriber> subscriberMap = new HashMap<String, ISubscriber>();
	public Map<String, ISubscriber> getSubscriberMap() {
		return subscriberMap;
	}
		
	@Override
	public void addSubscriberMap (String name, ISubscriber subscriber) { 
		this.subscriberMap.put (name, subscriber);
	}

	@Override
	public ISubscriber getSubscriber (String type) {
		ISubscriber subscriber
		subscriberMap.get(type);
		return subscriber;
	}
}