package com.reservation.foodTable.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.foodTable.entity.Area;
import com.reservation.foodTable.entity.Member;
import com.reservation.foodTable.repository.AreaRepository;

@Service
@Transactional(readOnly = true)
public class AreaService {
	
	// ====
	
	private final AreaRepository areaRepository;
	
	

	public AreaService(AreaRepository areaRepository) {

		this.areaRepository = areaRepository;
	}

	public List<Area> getAreaParent() {

		return (List<Area>) areaRepository.findByParent();
	}

	public List<Area> findByName(Integer id) {
		return (List<Area>) areaRepository.findByName(id);
	}
	
	public List<Integer> findByNames(String city, String district, String neighborhood) {
		List<Integer> area = new ArrayList<>();
		area.add(areaRepository.findByName(city));
		area.add(areaRepository.findByName(district));
		area.add(areaRepository.findByName(neighborhood));
		return area;
	}

	public Area get(Integer integer) {
		System.out.println(integer);
		return areaRepository.findById(integer).get();
	}

	public List<Area> findMyArea(Member member) {
		// TODO Auto-generated method stub
		return areaRepository.findMyArea(member);
	}

}
