package com.reservation.foodTable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservation.foodTable.entity.ReservationOrderInfo;

public interface ReservationOrderInfoRepository extends JpaRepository<ReservationOrderInfo, Integer> {
	
	
	/*
	 * 3월 10일 오전 8시 40분
	 * 홍한별
	 * 부모인 예약정보가 삭제될때 한번에 예약에 걸려있는 예약 상세 정보들을 삭제하기 위해 사용하는 메서드
	 * 8시 55분
	 * in 쿼리가 안나가고 개별적으로 나가서 일단 스탑
	 * */
	// public void deleteByReservationInfoId(int id);
	
	
	/*	3월 10일 오전 10시 00분
	 *  해당 예약의 주문 정보를 가져오는 함수
	 * */
	
	public List<ReservationOrderInfo> findByReservationInfoId(int id);
}
