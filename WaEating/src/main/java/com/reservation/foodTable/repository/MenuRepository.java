package com.reservation.foodTable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.foodTable.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
	
	@Query("SELECT m FROM Menu m join fetch m.menuCategory mc WHERE m.restaurant.id=:id ORDER BY mc.orderNumber DESC")
	public List<Menu> findByRestaurant_Id(@Param("id")int id);
	// 위의 함수를 이름으로만 작성한다면 List<Menu> findByRestaurant_IdOrderByMenuCategory_OrderNumberDesc(int id); 
	
	@Query("SELECT m FROM Menu m WHERE m.name LIKE %:name%")
	public List<Menu> findByMenu(@Param("name")String name);
	// 위의 함수를 함수명으로만 작성한다면  public List<Menu> findByNameContaining(String name);
	
	@Modifying
	@Query("UPDATE Menu m SET m.representative = false WHERE m.restaurant.id=:id ")
	public void representFalse(@Param("id")Integer restaurantId);
	
	@Modifying
	@Query("UPDATE Menu m SET m.representative = true WHERE m.id in :menuids ")
	public void representRegister(@Param("menuids")List<Integer> menuIds);
}
