package com.reservation.foodTable.controller.reservation;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.foodTable.Service.ReservationInfoService;
import com.reservation.foodTable.dto.OrderInfoDTO;

@RestController
public class ReservationRestController {

	private final ReservationInfoService reservationInfoService;

	public ReservationRestController(ReservationInfoService reservationInfoService) {

		this.reservationInfoService = reservationInfoService;

	}

	@PostMapping("/restaurant/reservation/{reservationId}/menus")
	public String reservationMenus(@PathVariable("reservationId") int id, @RequestBody() Map<String, Object> orderInfo,
			HttpSession session) {
		System.out.println(orderInfo);
		Integer modifyId = null;
		if (session.getAttribute("modifyReservationId") != null) {
			modifyId = (Integer) session.getAttribute("modifyReservationId");
		}
		if (modifyId != null && modifyId == id) {
			System.out.println("이미 존재하는 예약 주문 변경인가봐요");
			session.setAttribute("modifyReservationOrderList", orderInfo);
			return "OK";
		}
		System.out.println("orderInfo : " + orderInfo);
		ArrayList<Map<String, Object>> orderMenus = (ArrayList<Map<String, Object>>) orderInfo.get("data");
		Integer totalPrice = Integer.parseInt((String) orderInfo.get("price"));

		reservationInfoService.reservationMenus(id, orderMenus, totalPrice);
		// 어떤 때에 No를 반환해서 앞에서 잘못됬음을 알려할까?
		return "OK";
	}

	@PostMapping("/restaurant/reservation/{reservationId}/menus2")
	public String reservationMenustest(@PathVariable("reservationId") int id, @RequestBody() OrderInfoDTO orderInfo,
			HttpSession session) {

		Integer modifyId = null;
		if (session.getAttribute("modifyReservationId") != null) {
			modifyId = (Integer) session.getAttribute("modifyReservationId");
			if (modifyId == id) {
				System.out.println("이미 존재하는 예약 주문 변경인가봐요");
				session.setAttribute("modifyReservationOrderList", orderInfo);

				return "OK";
			}
		}

		reservationInfoService.reservationMenus(id, orderInfo);
		// 어떤 때에 No를 반환해서 앞에서 잘못됬음을 알려할까?
		return "OK";
	}
}
