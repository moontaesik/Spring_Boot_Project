package com.reservation.foodTable.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.dto.AvailabilityData;
import com.reservation.foodTable.dto.OrderDTO;
import com.reservation.foodTable.dto.OrderinfoDTOvOwner;
import com.reservation.foodTable.dto.ReservationDTO;
import com.reservation.foodTable.embeddedType.Score;
import com.reservation.foodTable.entity.ReservationInfo;
import com.reservation.foodTable.entity.Restaurant;

public interface ReservationInfoRepository extends JpaRepository<ReservationInfo, Integer> {
	
	
	@Query("SELECT new com.reservation.foodTable.dto.ReservationDTO(r.id,rest.name,rat.date,rad.time,r.people)"
			+" FROM ReservationInfo r JOIN r.restaurant rest JOIN r.restaurantAvaTime rat"
			+ " JOIN rat.restaurantDefaultTime rad WHERE r.id =:id")
	public ReservationDTO findConfrimInfo(@Param("id")Integer id);
	
	@Query("SELECT r FROM ReservationInfo r JOIN FETCH r.restaurant rest JOIN FETCH r.restaurantAvaTime rat"
			+ " JOIN FETCH rat.restaurantDefaultTime rad JOIN FETCH r.reservationOrderInfo re"
			+ " Join FETCH re.menu WHERE r.id =:id")
	public ReservationInfo findReservationInfoWithMenus(int id);

	@Query("SELECT r FROM ReservationInfo r JOIN FETCH r.restaurant rest JOIN FETCH r.restaurantAvaTime rat"
			+ " JOIN FETCH rat.restaurantDefaultTime rad WHERE r.id =:id")
	public ReservationInfo findReservationInfo(int id);
	
	@Query("SELECT r FROM ReservationInfo r WHERE r.member.id =:id ORDER BY r.restaurantAvaTime DESC")
	List<ReservationInfo> findByMemberId(@Param("id")Integer memberId);
	
	@Query("SELECT r.restaurant.id FROM ReservationInfo r WHERE r.id = :id ")
	public Integer findRestaurantId(@Param("id")Integer reservationInfo);
	
	@Query("SELECT r FROM ReservationInfo r WHERE r.member.id = :id AND "
	         + "TIMESTAMP(CONCAT(r.restaurantAvaTime.date, ' ', r.restaurantAvaTime.restaurantDefaultTime.time)) < CURRENT_TIMESTAMP "
	         + "ORDER BY TIMESTAMP(CONCAT(r.restaurantAvaTime.date, ' ', r.restaurantAvaTime.restaurantDefaultTime.time)) DESC")
	   public Page<ReservationInfo> findByMemberId(@Param("id") int keyword, Pageable pageable);
	
	@Query("SELECT new com.reservation.foodTable.embeddedType.Score(ROUND(COALESCE(AVG(re.score.tasteScore), 0),2), "
	         + "ROUND(COALESCE(AVG(re.score.serviceScore), 0),2), ROUND(COALESCE(AVG(re.score.priceScore), 0),2), ROUND(COALESCE(AVG(re.score.totalScore), 0),2)) "
	         + "FROM ReservationInfo r JOIN r.review re JOIN r.restaurantAvaTime rt "
	         + "WHERE YEAR(rt.date) = :year AND Month(rt.date) = :month AND r.restaurant.id = :id")
	   public Score findCoredate(@Param("year") int year, @Param("month") int month, @Param("id") int id);
	
	@Query("SELECT new com.reservation.foodTable.embeddedType.Score(ROUND(AVG(re.score.tasteScore),2),"
			+ " ROUND(AVG(re.score.serviceScore),2), ROUND(AVG(re.score.priceScore),2)) FROM ReservationInfo r "
			+ "INNER JOIN r.review re WHERE r.restaurant.id =:id2")
	public Score findCore(@Param("id2") Integer id);
	//태식 - 예약정보랑 리뷰랑 조인을 해서 review에 있는 모든 별점별로 평균을 구해서 소수점 두자리까지만 계산해서 가져옴, score클래스에서 총 별점을 계산에서 total에 넣어줌
	
	
	@Query("SELECT r FROM ReservationInfo r WHERE r.member.id =:id AND "
			+ "TIMESTAMP(CONCAT(r.restaurantAvaTime.date, ' ', r.restaurantAvaTime.restaurantDefaultTime.time)) >= CURRENT_TIMESTAMP "
			+ "ORDER BY TIMESTAMP(CONCAT(r.restaurantAvaTime.date, ' ', r.restaurantAvaTime.restaurantDefaultTime.time)) DESC")
	List<ReservationInfo> afterInfo(@Param("id") Integer memberId);
	//태식 - 지나지 않은 예약만 보여주고 시간 역순으로 가져옴
	@Query("SELECT DISTINCT new com.reservation.foodTable.dto.AvailabilityData(rd.id,rd.time) "
	         + "FROM ReservationInfo r JOIN r.restaurantAvaTime ra JOIN ra.restaurantDefaultTime rd "
	         + "WHERE r.restaurant.id = :id AND ra.date = :date ORDER BY rd.time ASC")
	   public List<AvailabilityData> getReservedTimes(@Param("id")int restaurantId,@Param("date")LocalDate selectedDate);

	   
	   @Query("SELECT rm.name,SUM(ri.amount) FROM ReservationInfo r JOIN r.restaurantAvaTime ra JOIN r.reservationOrderInfo ri JOIN ri.menu rm "
	         + "WHERE r.restaurant.id = :id AND ra.date = :date GROUP BY rm.name")
	   public List<Object[]> totalMenuData(@Param("id")int restaurantId, @Param("date")LocalDate today);
	   
	   
	//   @Query("SELECT rd.time,r.people,r.totalAmount,r.reservationPhone,r.reservationName,r.extraRequirement"
//	         + " FROM ReservationInfo r JOIN r.restaurantAvaTime ra JOIN ra.restaurantDefaultTime rd "
//	         + "WHERE r.restaurant.id = :id AND ra.date = :date")
	//   public List<Object[]> totalOrderInfo(@Param("id")int restaurantId, @Param("date")LocalDate today);
	   
	   @Query("SELECT new com.reservation.foodTable.dto.OrderinfoDTOvOwner(ra.date,rd.time,r.people,r.totalAmount,r.reservationPhone,r.reservationName,r.extraRequirement)"
	         + " FROM ReservationInfo r JOIN r.restaurantAvaTime ra JOIN ra.restaurantDefaultTime rd WHERE r.restaurant.id = :id AND"
	         + " ra.date=:date" )
	   public List<OrderinfoDTOvOwner> totalOrderInfo(@Param("id")int restaurantId,@Param("date")LocalDate today);
	   

		@Query("SELECT r "
				+ "FROM ReservationInfo r JOIN FETCH r.reservationOrderInfo ri Join FETCH ri.menu m WHERE r.id =:id")
		public ReservationInfo reservationDetailsById(@Param("id")Integer reservationId);
		
		@Query(value="SELECT r "
				+ "FROM ReservationInfo r JOIN FETCH r.restaurantAvaTime ra Join FETCH ra.restaurantDefaultTime rd "
				+ "WHERE r.restaurant.id =:id AND ra.date =:date "
				+ "ORDER BY rd.time ASC",
				countQuery="SELECT COUNT(r) FROM ReservationInfo r JOIN r.restaurantAvaTime ra WHERE r.restaurant.id =:id AND ra.date =:date")
		public Page<ReservationInfo> reservationDetailsById2(@Param("id")Integer reservationId,@Param("date")LocalDate date,Pageable pageable);
		
		
		@Query(value="SELECT r "
				+ "FROM ReservationInfo r JOIN FETCH r.restaurantAvaTime ra Join FETCH ra.restaurantDefaultTime rd "
				+ "WHERE r.restaurant.id =:id AND ra.date =:date AND rd.id=:time "
				+ "ORDER BY rd.time ASC",
				countQuery="SELECT COUNT(r) FROM ReservationInfo r JOIN r.restaurantAvaTime ra WHERE r.restaurant.id =:id AND ra.date =:date"
						+ " AND ra.restaurantDefaultTime.id=:time ")
		public Page<ReservationInfo> reservationDetailsById2(@Param("id")Integer reservationId,@Param("date")LocalDate date,
				@Param("time")Integer time,Pageable pageable);

		@Query("SELECT SUM(r.totalAmount) FROM ReservationInfo r JOIN r.restaurantAvaTime ra "
				+ "WHERE r.restaurant.id =:id AND ra.date =:date")
		public Integer reservationTotalPriceByDateAndTime(@Param("id")Integer reservationId,@Param("date")LocalDate date);
		
		@Query("SELECT SUM(r.totalAmount) FROM ReservationInfo r JOIN r.restaurantAvaTime ra "
				+ "WHERE r.restaurant.id =:id AND ra.date =:date AND ra.restaurantDefaultTime.id=:time" )
		public Integer reservationTotalPriceByDateAndTime(@Param("id")Integer reservationId,@Param("date")LocalDate date,
				@Param("time")Integer time);

		
		@Query("SELECT new com.reservation.foodTable.dto.OrderDTO(m.name,SUM(ri.amount)) "
				+ "FROM ReservationInfo r JOIN r.restaurantAvaTime ra "
				+ "JOIN r.reservationOrderInfo ri Join ri.menu m "
				+ "WHERE r.restaurant.id =:id AND ra.date =:date "
				+ "GROUP BY m.id")
		public List<OrderDTO> reservationOrderSheetByDateAndTime(@Param("id")Integer reservationId,@Param("date")LocalDate date);

		@Query("SELECT new com.reservation.foodTable.dto.OrderDTO(m.name,SUM(ri.amount)) "
				+ "FROM ReservationInfo r JOIN r.restaurantAvaTime ra "
				+ "JOIN r.reservationOrderInfo ri Join ri.menu m "
				+ "WHERE r.restaurant.id =:id AND ra.date =:date AND ra.restaurantDefaultTime.id=:time "
				+ "GROUP BY m.id")
		public List<OrderDTO> reservationOrderSheetByDateAndTime(@Param("id")Integer reservationId,@Param("date")LocalDate date,
				@Param("time")Integer time);
		
		@Query("SELECT re FROM ReservationInfo r JOIN r.restaurant re where r.id=:id")
		public Restaurant restaurantImg(@Param("id")int id);
}
