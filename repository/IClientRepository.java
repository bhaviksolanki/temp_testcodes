
@Repository
public interface IClientRepository extends JpaRepository<Client, Integer> {
	public Client findByClientId(String clientId);
}