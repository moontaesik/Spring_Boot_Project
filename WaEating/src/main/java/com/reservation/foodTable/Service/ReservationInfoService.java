package com.reservation.foodTable.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.foodTable.dto.AvailabilityData;
import com.reservation.foodTable.dto.GraphDTO;
import com.reservation.foodTable.dto.OrderDTO;
import com.reservation.foodTable.dto.OrderInfoDTO;
import com.reservation.foodTable.dto.OrderinfoDetailsDTO;
import com.reservation.foodTable.dto.OrderinfoDetailsPageDTO;
import com.reservation.foodTable.dto.ReservationDTO;
import com.reservation.foodTable.dto.ReservationDTOAll;
import com.reservation.foodTable.embeddedType.Score;
import com.reservation.foodTable.entity.Member;
import com.reservation.foodTable.entity.Menu;
import com.reservation.foodTable.entity.ReservationInfo;
import com.reservation.foodTable.entity.ReservationOrderInfo;
import com.reservation.foodTable.entity.Restaurant;
import com.reservation.foodTable.entity.RestaurantAvaTime;
import com.reservation.foodTable.exception.NotEnoughAvailableTablesException;
import com.reservation.foodTable.repository.MenuRepository;
import com.reservation.foodTable.repository.ReservationInfoRepository;
import com.reservation.foodTable.repository.ReservationOrderInfoRepository;
import com.reservation.foodTable.repository.RestaurantAvaTimeRepository;
import com.reservation.foodTable.repository.RestaurantRepository;

@Service
@Transactional
public class ReservationInfoService {

	private final ReservationInfoRepository reservationInfoRepository;
	private final RestaurantAvaTimeRepository restaurantAvaTimeRepository;
	private final ReservationOrderInfoRepository reservationOrderInfoRepository;
	private final MenuRepository menuRespository;
	private final RestaurantRepository restaurantRepository;

	
	private final EntityManager entitimanager;
	public ReservationInfoService(ReservationInfoRepository reservationInfoRepository,
			RestaurantAvaTimeRepository restaurantAvaTimeRepository,
			ReservationOrderInfoRepository reservationOrderInfoRepository, MenuRepository menuRespository,
			RestaurantRepository restaurantRepository,EntityManager entitimanager) {

		this.reservationInfoRepository = reservationInfoRepository;
		this.restaurantAvaTimeRepository = restaurantAvaTimeRepository;
		this.reservationOrderInfoRepository = reservationOrderInfoRepository;
		this.menuRespository = menuRespository;
		this.restaurantRepository = restaurantRepository;
		this.entitimanager = entitimanager;
	}

	/*
	 * 3월 10일 한별 1. public ReservationInfo reservation(int restaurantId , int
	 * avaTimeId,int bookPeople) throws NotEnoughAvailableTablesException 2. public
	 * ReservationDTO findReservationInfo(int id) 3. public int
	 * cancelReservation(int reservationId) 4. public void reservationMenus(int id,
	 * ArrayList<Map<String, Object>> orderMenus, int totalPrice) 5. public
	 * ReservationDTOAll findReservationInfoWithMenus(int id)
	 */

	@Transactional
	public ReservationInfo reservation(int restaurantId, int memberId, int avaTimeId, int bookPeople)
			throws NotEnoughAvailableTablesException {

		System.out.println("bookPeople : " + bookPeople);
		RestaurantAvaTime restaurantAvaTime = restaurantAvaTimeRepository.findById(avaTimeId).orElseThrow(null);
		restaurantAvaTime.reduceAvailableTables(bookPeople);
		// 예약한 예약 날짜에 가능 테이블 수를 줄여야 한다.

		ReservationInfo reservationInfo = new ReservationInfo(new Member(memberId), avaTimeId, restaurantId,
				bookPeople);
		ReservationInfo savedResult = reservationInfoRepository.save(reservationInfo);

		return savedResult;
	}

	@Transactional
	public ReservationDTO findReservationInfo(int id) {

		System.out.println("Here : " + reservationInfoRepository.findConfrimInfo(id));
		return reservationInfoRepository.findConfrimInfo(id);
	}

	@Transactional
	public int cancelReservation(int reservationId) {
		System.out.println("cancelReservation() 시작");
		ReservationInfo reservationInfo = reservationInfoRepository.findById(reservationId).get();
		int restaurnatId = reservationInfo.getRestaurant().getId();
		int avaTimeId = reservationInfo.getRestaurantAvaTime().getId();
		RestaurantAvaTime restaurantAvaTime = restaurantAvaTimeRepository.findById(avaTimeId).get();
		int people = reservationInfo.getPeople();
		restaurantAvaTime.expandAvailableTables(people);
//		if(!reservationInfo.getReservationOrderInfo().isEmpty()) {
//			reservationOrderInfoRepository.deleteByReservationInfoId(restaurnatId);
//		}
		reservationInfoRepository.deleteById(reservationId);
		System.out.println("cancelReservation() 끝");
		return restaurnatId;

	}

	@Transactional
	public void reservationMenus(int id, ArrayList<Map<String, Object>> orderMenus, int totalPrice) {
		// TODO Auto-generated method stub
		ReservationInfo reservationInfo = reservationInfoRepository.findById(id).get();

		Set<ReservationOrderInfo> orderMenusSet = reservationInfo.getReservationOrderInfo();
		orderMenus.forEach(data -> {
			System.out.println("data.get(\"quantity\") Type :  " + data.get("quantity").getClass() + " 값 : "
					+ data.get("quantity"));
			System.out.println("data.get(\"id\") Type :  " + data.get("id").getClass());
			System.out.println("data.get(\"id\") 값 :  " + data.get("id").getClass());
			orderMenusSet.add(new ReservationOrderInfo((Integer) data.get("quantity"),
					new Menu(Integer.parseInt((String) data.get("id"))), reservationInfo));
		});
		reservationInfo.changeTotalPrice(totalPrice);

	}

	public ReservationDTOAll findReservationInfoWithMenus(int id) {
		// TODO Auto-generated method stub
		ReservationDTOAll data = new ReservationDTOAll(reservationInfoRepository.findReservationInfoWithMenus(id));
		return data;
	}

	public ReservationDTOAll findReservationInfoWithMenus(OrderInfoDTO data, int id) {

		// 필요한 것 메뉴 리스트 + reservationInfo
		List<Integer> menuIds = new ArrayList<>();
		data.getData().forEach(o -> {
			menuIds.add(o.getId());
		});
		ReservationDTOAll datas = new ReservationDTOAll(reservationInfoRepository.findReservationInfo(id), data,
				menuRespository.findAllById(menuIds));
		return datas;
	}

	public void getReservationById(int id) {
		// TODO Auto-generated method stub

	}

	/*
	 * 3월 10일 태식 영수 1. public List<ReservationDTO> findByMemberId(Integer id) 2.
	 * public ReservationInfo findById(Integer reservationid)
	 */
	public List<ReservationDTO> findByMemberId(Integer id) {

		return reservationInfoRepository.findByMemberId(id).stream().map(o -> {
			ReservationDTO reservationDTO = new ReservationDTO(o);
			reservationDTO.setHasComment(o.getReview() != null);
			reservationDTO.setAmount(o.getTotalAmount());
			return reservationDTO;
		}).toList();

	}

	public ReservationInfo findById(Integer reservationid) {

		return reservationInfoRepository.findById(reservationid).get();
	}

	@Transactional
	public void reservationMenus(int id, OrderInfoDTO orderInfo) {
		// TODO Auto-generated method stub
		System.out.println("===================================asdsadasd");
		ReservationInfo reservationInfo = reservationInfoRepository.findById(id).get();

		Set<ReservationOrderInfo> orderMenusSet = reservationInfo.getReservationOrderInfo();
		orderMenusSet.clear();
		orderInfo.getData().forEach(o -> {
			orderMenusSet.add(new ReservationOrderInfo(o.getQuantity(), new Menu(o.getId()), reservationInfo));
		});
		reservationInfo.changeTotalPrice(orderInfo.getPrice());
		entitimanager.flush();
		entitimanager.clear();
	}

	private final int reservation_PER_PAGE = 5;

	public Page<ReservationDTO> listByPage(int num, String sortField, String sortDir, int keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(num - 1, reservation_PER_PAGE, sort);

		return reservationInfoRepository.findByMemberId(keyword, pageable).map(o -> {
			ReservationDTO reservationDTO = new ReservationDTO(o);
			reservationDTO.setHasComment(o.getReview() != null);
			reservationDTO.setAmount(o.getTotalAmount());
			return reservationDTO;
		});
	}

	public Map<String, GraphDTO> findByRestaurantId2(Integer month, int id) {

		GraphDTO totalGraphDTO = new GraphDTO("Total Scroe");
		GraphDTO priceGraphDTO = new GraphDTO("Price Score");
		GraphDTO tasteGraphDTO = new GraphDTO("Taste Score");
		GraphDTO serviceGraphDTO = new GraphDTO("Service Score");
		Map<String, GraphDTO> graphDTOMap = new HashMap<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월");

		for (int i = month; i >= 0; i--) {
			LocalDate months = LocalDate.now().minusMonths(i);
			String label = months.format(formatter);
			Score score = reservationInfoRepository.findCoredate(months.getYear(), months.getMonthValue(), id);
			totalGraphDTO.addData(label, score.getTotalScore());
			priceGraphDTO.addData(label, score.getPriceScore());
			tasteGraphDTO.addData(label, score.getTasteScore());
			serviceGraphDTO.addData(label, score.getServiceScore());
		}
		graphDTOMap.put("total", totalGraphDTO);
		graphDTOMap.put("price", priceGraphDTO);
		graphDTOMap.put("taste", tasteGraphDTO);
		graphDTOMap.put("service", serviceGraphDTO);

		return graphDTOMap;
	}

	// 태식 - 리뷰작성하면 식당에 있는 별점 업데이트 해주기
	@Transactional
	public void findCore(Integer id) {
		// 식당에 맞게 작성된 리뷰에 별점들을 계산에서 넣어줌
		Score score = reservationInfoRepository.findCore(id);
		Restaurant restaurant = restaurantRepository.findById(id).get();
		restaurant.chageScroe(score);
		// 그 식당에 계산된 별점을 넣어줌
	}

	// 태식 - 아직 지나지 않은 예약 목록 가져오기
	public List<ReservationDTO> afterInfo(Integer id) {

		return reservationInfoRepository.afterInfo(id).stream().map(o -> {
			ReservationDTO reservationDTO = new ReservationDTO(o);
//			reservationDTO.setHasComment(o.getReview() != null);
//			reservationDTO.setAmount(o.getTotalAmount());
			return reservationDTO;
		}).toList();

	}

	public boolean isMyReservation(Integer id, int reservationId) {
		// TODO Auto-generated method stub
		ReservationInfo reservationInfo = reservationInfoRepository.findById(reservationId)
				.orElse(new ReservationInfo());
		System.out.println("(isMyReservation)reservationInfo : " + reservationInfo);
		System.out.println("(isMyReservation)userId : " + id);
		return reservationInfo.getMember().getId() == id;
	}

	@Transactional
	public void reservationDetails(int id, String name, String phone, String details, String merchantUid, String impUid) {

		ReservationInfo reservationInfo = reservationInfoRepository.findById(id).get();
		reservationInfo.setReservationName(name);
		reservationInfo.setReservationPhone(phone);
		reservationInfo.setExtraRequirement(details);
		reservationInfo.setMerchantUid(merchantUid);
		reservationInfo.setImpUid(impUid);
	}

	public List<AvailabilityData> reservationedDateTimes(int restaurantId, LocalDate selectedDate) {

		return reservationInfoRepository.getReservedTimes(restaurantId, selectedDate);
	}

	public List<Object[]> totalMenuData(int restaurantId, LocalDate today) {
		// TODO Auto-generated method stub
		return reservationInfoRepository.totalMenuData(restaurantId, today);
	}

	public OrderinfoDetailsDTO reservationDetailsById(Integer reservationId) {
		// TODO Auto-generated method stub
		return new OrderinfoDetailsDTO(reservationInfoRepository.reservationDetailsById(reservationId));
	}

	public OrderinfoDetailsPageDTO reservationDetailsById2(int restaurantId, LocalDate selectedDate, Integer time, Pageable pageable) {
		// TODO Auto-generated method stub
		
		if(time==0 || time==null) {
			
			return new OrderinfoDetailsPageDTO(
					reservationInfoRepository.reservationDetailsById2(restaurantId,selectedDate,pageable));
		}else {
			return new OrderinfoDetailsPageDTO(
					reservationInfoRepository.reservationDetailsById2(restaurantId,selectedDate,time,pageable));
		}
		
		
		// return new OrderinfoDetailsDTO(reservationInfoRepository.reservationDetailsById(reservationId,LocalDate.now(),PageRequest.of(0, 10)));
	}

	public Integer reservationTotalPriceByDateAndTime(int restaurantId, LocalDate selectedDate, Integer time) {
		// TODO Auto-generated method stub
		
		if(time==0 || time==null) {
			return reservationInfoRepository.reservationTotalPriceByDateAndTime(restaurantId,selectedDate);
		}else{
			return reservationInfoRepository.reservationTotalPriceByDateAndTime(restaurantId,selectedDate,time);
		}
		
	}

	public List<OrderDTO> reservationOrderSheetByDateAndTime(int restaurantId, LocalDate selectedDate, Integer time) {
		// TODO Auto-generated method stub
		if(time==0 || time==null) {
			return reservationInfoRepository.reservationOrderSheetByDateAndTime(restaurantId,selectedDate);
		}else{
			return reservationInfoRepository.reservationOrderSheetByDateAndTime(restaurantId,selectedDate,time);
		}
	}

	public String restaurantImg(int id) {
		// TODO Auto-generated method stub
		return reservationInfoRepository.restaurantImg(id).photosImagePath();
	}

	

}
