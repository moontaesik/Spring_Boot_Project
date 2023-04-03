package com.reservation.foodTable.controller.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.foodTable.Service.AreaService;
import com.reservation.foodTable.dto.AreaSplitDTO;

@RestController
public class ManagerRestController {
	@Autowired
	private AreaService areaRepo;
	
	
	@PostMapping("/addressCheck")
	public List<Integer> checkAddress(@RequestBody AreaSplitDTO area) {
		System.out.println(area.getCity() + area.getGu() + area.getDong());
		return areaRepo.findByNames(area.getCity(),area.getGu(),area.getDong());
		
	}

}
