package com.reservation.foodTable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.reservation.foodTable.entity.Area;
import com.reservation.foodTable.entity.Member;


@Repository
public interface AreaRepository extends JpaRepository<Area, Integer>{
	
	@Query("SELECT a FROM Area a WHERE a.parent = null")
	List<Area> findByParent();

	@Query("SELECT a FROM Area a WHERE a.parent.id = :id")
	List<Area> findByName(Integer id);
	
	//문제가 있긴함 나중에 추후 수정할 예정 같은 이름의 지역이 들어왔을 경우 에러가 생김 구 동 부터는 parent id를 같이 넘겨주면 될듯
	@Query("SELECT a.id FROM Area a WHERE a.name LIKE :name")
	Integer findByName(@Param("name")String city);
	
	@Query("SELECT a FROM Area a JOIN MemberInterestArea ma ON a.id = ma.area.id WHERE ma.member=:member")
	List<Area> findMyArea(@Param("member")Member member);

}
