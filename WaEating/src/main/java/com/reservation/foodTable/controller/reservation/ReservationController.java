package com.reservation.foodTable.controller.reservation;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reservation.foodTable.Service.MemberService;
import com.reservation.foodTable.Service.MenuService;
import com.reservation.foodTable.Service.ReservationInfoService;
import com.reservation.foodTable.Service.ReservationOrderInfoService;
import com.reservation.foodTable.dto.OrderDTO;
import com.reservation.foodTable.dto.OrderInfoDTO;
import com.reservation.foodTable.dto.ReservationDTOAll;
import com.reservation.foodTable.entity.Member;
import com.reservation.foodTable.entity.ReservationInfo;
import com.reservation.foodTable.exception.NotEnoughAvailableTablesException;

@Controller
public class ReservationController {

	private final MenuService menuService;
	private final ReservationInfoService reservationInfoService;
	private final MemberService memberService;
	private final ReservationOrderInfoService reservationOrderInfoService;

	public ReservationController(ReservationInfoService reservationInfoService, MenuService menuService,
			MemberService memberService, ReservationOrderInfoService reservationOrderInfoService) {
		this.reservationInfoService = reservationInfoService;
		this.menuService = menuService;
		this.memberService = memberService;
		this.reservationOrderInfoService = reservationOrderInfoService;
	}
	@PostMapping("/restaurant/reservation")
	public String error() {
		// reservation 하는 멤버 를 principal 에서 가져와야하는 과정을 추가해야한다.

		return "error";

	}
	
	@PostMapping("/restaurant/reservation/{restaurantId}")
	public String reservation(@PathVariable("restaurantId") int id, Model model, int reservationDateTimeId,
			int bookPeople, RedirectAttributes rttr, Principal p) {
		// reservation 하는 멤버 를 principal 에서 가져와야하는 과정을 추가해야한다.

		if (p == null) {

			return "redirect:/restaurant/" + id;
		}

		int memberId = memberService.findByUserId(p.getName()).getId();

		try {
			ReservationInfo reservationInfo = reservationInfoService.reservation(id, memberId, reservationDateTimeId,
					bookPeople);
			System.out.println("(reservation)reservationInfo : " + reservationInfo);
			return "redirect:/restaurant/reservation/" + id + "/" + reservationInfo.getId();
		} catch (NotEnoughAvailableTablesException e) {
			// TODO Auto-generated catch block
			// 예외 처리하는 부분 해야됨
			rttr.addFlashAttribute("message", e.getMessage());
			return "redirect:/restaurant/" + id;
		}

	}

	@PostMapping("/restaurant/reservation/{reservationId}/cancel")
	public String reservationCancel(@PathVariable("reservationId") int reservationId) {

		System.out.println("cancel : " + reservationId);
		int restaurantId = reservationInfoService.cancelReservation(reservationId);

		return "redirect:/restaurant/" + restaurantId;
	}

	// ==============================================================

	@PostMapping("/restaurant/reservation/{reservationId}/paymentPage")
	public String reservationPaymentPage(@PathVariable("reservationId") int id, Model model, HttpSession session) {

		Integer modifyId = null;
		if (session.getAttribute("modifyReservationId") != null) {
			modifyId = (Integer) session.getAttribute("modifyReservationId");
			if (modifyId == id) {
				OrderInfoDTO data = (OrderInfoDTO) session.getAttribute("modifyReservationOrderList");
				model.addAttribute("data", reservationInfoService.findReservationInfoWithMenus(data, id)); 
				model.addAttribute("imgPath",reservationInfoService.restaurantImg(id));
				return "restaurant_reservation_payment";
			}
		}
		
		model.addAttribute("imgPath",reservationInfoService.restaurantImg(id));
		model.addAttribute("data", reservationInfoService.findReservationInfoWithMenus(id));
		return "restaurant_reservation_payment";
	}
	
	@GetMapping("/restaurant/reservation/{reservationId}/details")
	public String myReservation(@PathVariable("reservationId") int id,Model model){
		model.addAttribute("data", reservationInfoService.findReservationInfoWithMenus(id));
		return "restaurant_reservation_confirm";
	}
	@PostMapping("/restaurant/reservation/{reservationId}/payment")
	public String reservationPayment(@PathVariable("reservationId") int id, Model model, HttpSession session,
			@RequestParam("name") String name, @RequestParam("phone") String phone,
			@RequestParam(value = "details", required = false) String details,
			@RequestParam("merchantUid")String  merchantUid,
			@RequestParam("impUid")String  impUid) {

		Integer modifyId = null;
		if (session.getAttribute("modifyReservationId") != null) {
			modifyId = (Integer) session.getAttribute("modifyReservationId");
			if (modifyId == id) {
				OrderInfoDTO data = (OrderInfoDTO) session.getAttribute("modifyReservationOrderList");
				reservationInfoService.reservationMenus(id, data);

			}
		}
		reservationInfoService.reservationDetails(id, name, phone, details,merchantUid,impUid);

		System.out.println("a: " + reservationInfoService.findReservationInfoWithMenus(id));
		model.addAttribute("data", reservationInfoService.findReservationInfoWithMenus(id));
		return "restaurant_reservation_confirm";
	}

	@PostMapping("/restaurant/reservation/{reservationId}/modify")
	public String reservationModify(@PathVariable("reservationId") int id, Model model, HttpSession session) {

		session.setAttribute("modifyReservationId", id);
		List<OrderDTO> r = reservationOrderInfoService.findOrderInfos(id);
		System.out.println(r);
		model.addAttribute("orderList", r);
		model.addAttribute("reservationInfo", reservationInfoService.findReservationInfo(id));
		model.addAttribute("menuList", menuService.retrieveMenusByReservationId(id));
		return "restaurant_reservation_menu";

	}

	@GetMapping("/restaurant/reservation/{restaurantId}/{reservationId}")
	public String reservationMenus(@PathVariable("restaurantId") int id,
			@PathVariable("reservationId") int reservationId, Model model, Principal p) {
		// 여기서 reservationId의 userid가 자신인지 확인해서 아닌경우 비정상적인 접근임을 보여주자
		Member member = memberService.findByUserId(p.getName());
		System.out.println("member : " + member);
		if (!reservationInfoService.isMyReservation(member.getId(), reservationId)) {
			// 뭐라고 해야할지 아직 미정
			return "redirect:/";
		}
		model.addAttribute("reservationInfo", reservationInfoService.findReservationInfo(reservationId));
		model.addAttribute("menuList", menuService.retrieveMenus(id));
		return "restaurant_reservation_menu";
	}
	
	

}
