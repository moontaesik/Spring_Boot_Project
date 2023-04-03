package com.reservation.foodTable.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.Transient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.entity.Area;
import com.reservation.foodTable.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>,RestaurantRepositoryCustom {
	
	@Query("SELECT r FROM Restaurant r join fetch r.si join fetch r.dong join fetch r.gun"
			+ " WHERE r.id=:id")
	public Optional<Restaurant> findOneById(@Param("id") int id);
	
	//  ================================ < 하지원 > ================================
	
	// 해결 해야 하는 방안 Area 레벨에 따라 si gun dong 으로 각각 검색 될 수 있도록
	  
	public interface HighScoreRestaurant {
		    // Get methods
		public Integer getId();
	    public String getResName();
	    public Long getCountRv();
	    public Integer getCountReview();
	    public float getTotalScore();
	    public Integer getCategoryId();
	    public String getImg();
	    public String getSi();
	    public String getGun();
	    public String getDong();
	    public String getResIntro();
        public String getCatName();	        
        public int getCatId();
	    // Set methods
	    public void setSi(String si);
	    public void setGun(String gun);
	    public void setDong(String dong);

	    default public String getInfo() {
	        return "Restaurant [getResName=" + getResName() + ", getCountRv=" + getCountRv() + ", getTotalScore=" + getTotalScore() + 
	        		", getSi=" + getSi() + ", getGun=" + getGun( ) + ", getDong=" + getDong() + ", getResIntro=" + getResIntro() +  ", getCatName=" + getCatName() + ", getCountReview=" + getCountReview() + ", getImg=" + getImg() +"]";
	    }
	    
		@Transient
		public default String photosImagePath() {
			if (this.getId() == null || this.getImg() == null) {
				return "/img/12.png";
			}
			return "/restaurant-photos/" + this.getId() + "/restaurant-Img/" + this.getImg();
		}
	} 
	

    
    // ✔ 비회원일 경우 별점순 가게리스트를 가져오는 쿼리문 
    @Query("SELECT r.img as img, r.id as id,r.si.name as si, r.gun.name as gun, r.dong.name as dong, COUNT(i.id) as countRv, "
    		+ "r.resIntro as resIntro, r.name as resName, r.score.totalScore as totalScore "
    		+ "FROM Restaurant r "
    		+ "LEFT JOIN ReservationInfo i ON r.id = i.restaurant.id "
            + "GROUP BY r.id  ORDER BY r.score.totalScore DESC, countRv DESC")
    public List<HighScoreRestaurant> findTopScoreRestaurant(Pageable pageable);

    // ✔ 회원일 경우 관심지역과 관심카테고리 내에서 별점순 가게리스트를 가져오는 쿼리문
    @Query("SELECT r.img as img,r.id as id,r.si.name as si, r.gun.name as gun, r.dong.name as dong, COUNT(distinct i.id) as countRv, "
    		+ "r.resIntro as resIntro, r.name as resName, r.score.totalScore as totalScore "
    		+ "FROM Restaurant r LEFT JOIN ReservationInfo i ON r.id = i.restaurant.id "
    		+ "left Join r.restaurantCategory rc "
            + "where (r.si.id in :area or r.gun.id in :area or r.dong.id in :area) and rc.category.id in :category "
            + "Group By r.id "
            + "ORDER BY r.score.totalScore DESC, countRv DESC")
    public List<HighScoreRestaurant> findTopScoreRestaurant(@Param("area") List<Integer> areaIds,@Param("category") 
    List<Integer> categoryIds,Pageable pageable);
   
    
    @Query("SELECT m.category.id FROM MemberInterestCategory m WHERE m.member.id = :num")
    public List<Integer> getUserInterestCategories(@Param("num") int id);

    @Query("SELECT m.area.id FROM MemberInterestArea m WHERE m.member.id = :num")
    public List<Integer> getUserInterestArea(@Param("num") int id);

    @Query("SELECT r.img as img,r.id as id,r.si.name as si, r.gun.name as gun, r.dong.name as dong, COUNT(*) as countReview, r.resIntro as resIntro, r.name as resName, r.score.totalScore as totalScore from Restaurant r "
    		+ "LEFT JOIN ReservationInfo ri on r.id = ri.restaurant "
    		+ "JOIN Review rv on rv.reservationInfo.id = ri.id "
    		+ "JOIN RestaurantAvaTime as rat on rat.id = ri.restaurantAvaTime "
    		+ "WHERE rat.date >= :date GROUP BY r.id ORDER BY countReview asc")
    public List<HighScoreRestaurant> findTopReviewCountRestaurant(@Param("date") LocalDate date,Pageable pageable);


    @Query("SELECT r.img as img,r.id as id,r.si.name as si, r.gun.name as gun, r.dong.name as dong, COUNT(Distinct rv.id) as countReview, r.resIntro as resIntro, r.name as resName, r.score.totalScore as totalScore from Restaurant r "
    		+ "LEFT JOIN ReservationInfo ri on r.id = ri.restaurant "
    		+ "JOIN Review rv on rv.reservationInfo.id = ri.id "
    		+ "JOIN RestaurantAvaTime as rat on rat.id = ri.restaurantAvaTime "
    		+ "JOIN RestaurantCategory as rc ON rc.restaurant.id = r.id " 
    		+ "WHERE rat.date >= :date and (r.si.id in :area or r.gun.id in :area or r.dong.id in :area) "
    		+ "and rc.category.id in :category Group By r.id ORDER BY countReview desc")
    public List<HighScoreRestaurant> findTopReviewCountRestaurant(@Param("date") LocalDate date, @Param("area") List<Integer> areaIds,@Param("category") List<Integer> categoryIds,Pageable pageable);


    @Query("select r.img as img,r.id as id,rrs.name as si, rrg.name as gun, rrd.name as dong, COUNT(*) as countRv, "
    		+ "r.resIntro as resIntro, r.name as resName, r.score.totalScore as totalScore  "
    		+ "from Restaurant r "
    		+ "JOIN ReservationInfo ri on r.id = ri.restaurant "
    		+ "JOIN RestaurantAvaTime rat on rat.id = ri.restaurantAvaTime.id "
    		+ "JOIN r.si rrs "
    		+ "JOIN r.gun rrg "
    		+ "JOIN r.dong rrd "
    		+ "WHERE rat.date >= :date "
    		+ "GROUP BY r.id ORDER BY countRv desc")
    public List<HighScoreRestaurant> findTopReservationCountRestaurant(@Param("date") LocalDate date,Pageable pageable);
  
    // ✔최근 한달간 예약 많은 순서대로 - 로그인
    @Query("select r.img as img,r.id as id,rrs.name as si, rrg.name as gun, rrd.name as dong, COUNT(*) as countRv, "
    		+ "r.resIntro as resIntro, r.name as resName, r.score.totalScore as totalScore  "
    		+ "from Restaurant r "
    		+ "JOIN ReservationInfo ri on r.id = ri.restaurant "
    		+ "JOIN RestaurantAvaTime rat on rat.id = ri.restaurantAvaTime.id "
    		+ "JOIN r.si rrs "
    		+ "JOIN r.gun rrg "
    		+ "JOIN r.dong rrd "
    		+ "WHERE rat.date >= :date AND r.id in (SELECT DISTINCT rstc.restaurant.id From RestaurantCategory rstc WHERE rstc.category.id in :category) "
    		+ "AND (r.si.id in :area or r.gun.id in :area or r.dong.id in :area)"
    		+ "GROUP BY r.id ORDER BY countRv desc")
    public List<HighScoreRestaurant> findTopReservationCountRestaurant(@Param("date") LocalDate date, @Param("area") List<Integer> areaIds,@Param("category") List<Integer> categoryIds,Pageable pageable);
    
    //  ================================ < 오세종 > ================================
  
    @Query("SELECT r.name FROM Restaurant r")
    public List<Restaurant> findAllName();

    @Query("SELECT a.name FROM Area a WHERE a.name = : num")
    public Area getArea(@Param("num") String num);

	public Page<Restaurant> findByMemberName(String keyword, Pageable pageable);

	public Page<Restaurant> findByMemberPhone(String keyword, Pageable pageable);

	
}
