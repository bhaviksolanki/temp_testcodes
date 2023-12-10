
public interface IDistributionDetailRepository extends JpaRepository<DistributionDetail, Integer> {
	public Distribution Detail findByDistributionId (Integer distributionId);
}