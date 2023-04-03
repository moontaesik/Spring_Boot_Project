package com.reservation.foodTable.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reservation.foodTable.dto.OrderDTO;
import com.reservation.foodTable.entity.ReservationOrderInfo;
import com.reservation.foodTable.repository.ReservationOrderInfoRepository;

@Service
public class ReservationOrderInfoService {

	private final ReservationOrderInfoRepository reservationOrderInfoRepository;
	
	
	public ReservationOrderInfoService(ReservationOrderInfoRepository reservationOrderInfoRepository) {

		this.reservationOrderInfoRepository = reservationOrderInfoRepository;
	}


	public List<OrderDTO> findOrderInfos(int reservationId){
		
		List<ReservationOrderInfo> myOrderInfo = reservationOrderInfoRepository.findByReservationInfoId(reservationId);
		System.out.println("here : " +myOrderInfo);
		return myOrderInfo.stream().map(OrderDTO::new).toList();
	}


	public List<ReservationOrderInfo> findByReservationInfoId(Integer reservationid) {
		return reservationOrderInfoRepository.findByReservationInfoId(reservationid);
	}
}
