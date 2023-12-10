
public interface IProductionDistributionRepository extends JpaRepository<Product Distribution, ProDistUniqueID> { 
	public Product Distribution findByProDistUnique ID (ProDistUnique ID uniqueID);
}