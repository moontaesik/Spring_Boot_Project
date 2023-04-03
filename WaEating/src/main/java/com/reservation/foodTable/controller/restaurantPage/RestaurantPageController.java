package com.reservation.foodTable.controller.restaurantPage;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reservation.foodTable.Service.CategoryService;
import com.reservation.foodTable.Service.MemberService;
import com.reservation.foodTable.Service.MenuService;
import com.reservation.foodTable.Service.ReservationInfoService;
import com.reservation.foodTable.Service.RestaurantCategoryService;
import com.reservation.foodTable.Service.RestaurantDefaultTimeService;
import com.reservation.foodTable.Service.RestaurantService;
import com.reservation.foodTable.config.FileUploadUtil;
import com.reservation.foodTable.dto.AvailabilityData;
import com.reservation.foodTable.dto.GraphDTO;
import com.reservation.foodTable.dto.OrderinfoDetailsDTO;
import com.reservation.foodTable.dto.RestaurantEditDTO;
import com.reservation.foodTable.embeddedType.BusinessHour;
import com.reservation.foodTable.embeddedType.Score;
import com.reservation.foodTable.entity.Category;
import com.reservation.foodTable.entity.Menu;
import com.reservation.foodTable.entity.Restaurant;
import com.reservation.foodTable.entity.RestaurantDefaultTime;
import com.reservation.foodTable.enumClass.PriceRange;

@Controller
@RequestMapping("/restaurant-page")
public class RestaurantPageController {

	@Autowired
	private ReservationInfoService reservaser;
	@Autowired
	private MemberService memberService;
	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private RestaurantDefaultTimeService restaurantDefaultTimeService;

	@Autowired
	private RestaurantCategoryService restaurantCategoryService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MenuService menuService;
	// ============================ 페이지의 이동

	@GetMapping("/reservation")
	public String myReservation(@RequestParam(required = false, defaultValue = "") String date,
			@RequestParam(required = false, defaultValue = "0") Integer time,
			@PageableDefault(page = 0, size = 9) Pageable pageable, Principal p, Model model) {

		String loginOwnerId = p.getName();
		int restaurantId = memberService.myRestaurantId(loginOwnerId);
		if (restaurantId == 0) {

			// 에러 처리 어떻게 할까?
			return "";
		}

		LocalDate selectedDate;

		// String을 LocalDate로 변환할때 date의 값이 없거나 변환시 형시에 맞지 않을시 현재날자로 저장
		try {
			selectedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
		} catch (DateTimeParseException | NullPointerException e) {
			selectedDate = LocalDate.now();
		}
		;
		model.addAttribute("timeList", reservaser.reservationedDateTimes(restaurantId, selectedDate));
		model.addAttribute("orderSheet",
				reservaser.reservationOrderSheetByDateAndTime(restaurantId, selectedDate, time));
		model.addAttribute("totalPrice", String.format("%,d 원",
				reservaser.reservationTotalPriceByDateAndTime(restaurantId, selectedDate, time)));
		model.addAttribute("reservationInfo",
				reservaser.reservationDetailsById2(restaurantId, selectedDate, time, pageable));
		model.addAttribute("date", selectedDate);
		model.addAttribute("time", time);

		return "restaurantPage/reservation";
	}

	@GetMapping("/graph")
	public String statistics() {

		return "restaurantPage/graph";
	}
	@GetMapping("")
	public String home() {
		
		return "redirect:/restaurant-page/graph";
	}
	// =============================================================================================================================

	// 여기서 /graph/month/개월수 /graph/week/주 /graph/day/일자
	@GetMapping("/graph/{month}")
	@ResponseBody
	public Map<String, GraphDTO> statistics2(@PathVariable("month") Integer month, Principal p) {

		String loginOwnerId = p.getName();
		int restaurantId = memberService.myRestaurantId(loginOwnerId);
		if (restaurantId == 0) {

			// 에러 처리 어떻게 할까?
			return null;
		}

		// 여기서 Principal 객체로 부터 그 객체의 안의 RESTAURANT ID 를 가져와서 처리해야한다.

		return reservaser.findByRestaurantId2(month, restaurantId);
	}

	@GetMapping("/reservation/times")
	@ResponseBody
	public List<AvailabilityData> times(@RequestParam String date, Principal p) {

//		// 여기서 Principal 객체로 부터 그 객체의 안의 RESTAURANT ID 를 가져와서 처리해야한다.
//		int id = 1;

		String loginOwnerId = p.getName();
		int restaurantId = memberService.myRestaurantId(loginOwnerId);
		if (restaurantId == 0) {

			// 에러 처리 어떻게 할까?
			return null;
		}

		try {
			LocalDate localDate = LocalDate.parse(date);

			return reservaser.reservationedDateTimes(restaurantId, localDate);
		} catch (DateTimeParseException e) {

			// 어떤 처리를 할까?
			return null;
		}

	}

	@GetMapping("/reservation/details")
	@ResponseBody
	public OrderinfoDetailsDTO details(@RequestParam(value = "id") Integer reservationId, Principal p) {

		return reservaser.reservationDetailsById(reservationId);

	}

	/*****************************************************
	 * 오세종
	 *************************************************************/
	@GetMapping("/restaurant")
	public String editRestaurant(Principal p, Model model) {

		String client = p.getName();
		Integer restaurantId = memberService.myRestaurantId(client);

		Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
		List<RestaurantDefaultTime> defaultTime = restaurantDefaultTimeService.gettime(restaurant);
		RestaurantEditDTO restaurantEditDTO = new RestaurantEditDTO(restaurant.getOpenTime(), restaurant.getCloseTime(),
				restaurant.getPriceRange(), restaurant, defaultTime);
		restaurantEditDTO.setCategory(restaurantCategoryService.get(restaurantId));
		List<String> times = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			times.add(String.format("%02d:00", i));
		}

		List<String> myTimes = defaultTime.stream().map(time -> {
			return time.getTime().toString();
		}).toList();
		model.addAttribute("myTimes", myTimes);
		model.addAttribute("restaurantEdit", restaurantEditDTO);
		model.addAttribute("defaultTime", times);
		model.addAttribute("address", restaurantEditDTO.restaurant.getAdrress());

		return "restaurantPage/restaurantEdit";

	}

	@PostMapping("/restaurant/update")
	public String updateRestaurant(@ModelAttribute("restaurantEdit") RestaurantEditDTO restaurantEdit,
			@RequestParam(name = "time[]", required = false) String[] times, Model model) throws IOException {

		Restaurant originalRestaurant = restaurantService.getRestaurant(restaurantEdit.restaurant.getId());

		restaurantEdit.restaurant.setDong(originalRestaurant.getDong());
		restaurantEdit.restaurant.setSi(originalRestaurant.getSi());
		restaurantEdit.restaurant.setGun(originalRestaurant.getGun());
		restaurantEdit.restaurant.setSpecificArea(originalRestaurant.getSpecificArea());
		restaurantEdit.restaurant
				.setBusinessHour(new BusinessHour(restaurantEdit.getOpenTime(), restaurantEdit.getCloseTime()));
		restaurantEdit.restaurant.setPriceRange(PriceRange.valueOf(restaurantEdit.getPriceRange()));
		restaurantEdit.restaurant.setScore(new Score());
		Category category = categoryService.getCategory(restaurantEdit.getCategory());
		Restaurant saveRestaurant = restaurantService.update(restaurantEdit.restaurant, times);
		restaurantCategoryService.update(category, saveRestaurant);

		return "redirect:/restaurant-page/graph";
	}

	@GetMapping("/menu")
	public String listMenu(Principal p, Model model) {
		String client = p.getName();
		Integer restaurantId = memberService.myRestaurantId(client);

		List<Menu> menuList = menuService.getMenus(restaurantId);
		String restaurantName = restaurantService.getRestaurantName(restaurantId);
		model.addAttribute("menus", menuList);
		model.addAttribute("restaurantId", restaurantId);
		model.addAttribute("restaurantName", restaurantName);

		return "restaurantPage/menuList";

	}

	@PostMapping("/menu/represent/{restaurantId}")
	public String representMenu(@PathVariable("restaurantId") Integer restaurantId, Model model,
			@RequestParam("menu[]") List<Integer> menuIds) {

		menuService.menuRepresentRegister(restaurantId, menuIds);

		return "redirect:/restaurant-page/menu";
	}

	@GetMapping("/menu/add/{id}")
	public String addMenu(@PathVariable("id") Integer id, Model model) {
		Menu menu = new Menu();

		model.addAttribute("menu", menu);
		model.addAttribute("restaurantId", id);
		return "restaurantPage/menuEdit";
	}

	@GetMapping("/menu/edit/{id}")
	public String editMenu(@PathVariable("id") Integer id, Model model) {
		Menu menu = menuService.getMenu(id);
		model.addAttribute("menu", menu);
		model.addAttribute("restaurantId", menu.getRestaurant().getId());
		return "restaurantPage/menuEdit";
	}

	@GetMapping("/menu/delete/{id}")
	public String deleteMenu(@PathVariable("id") Integer id, Model model) {
		menuService.deleteMenu(id);

		return "redirect:/restaurant-page/menu";
	}

	@PostMapping("/menu/save")
	public String saveMenu(@RequestParam(name = "file", required = false) MultipartFile menuImg,
			@ModelAttribute("menu") Menu menu, @RequestParam("restaurantId") Integer id, Model model,
			RedirectAttributes redirectAttributes) throws IOException {
		Menu saveMenu;
		if (!menuImg.isEmpty()) {
			String fileName = StringUtils.cleanPath(menuImg.getOriginalFilename());
			menu.setImagePath(fileName);
			saveMenu = menuService.save(menu, id);
			String uploadDir = "restaurant-photos/" + saveMenu.getRestaurant().getId() + "/Menu-Img";
			FileUploadUtil.saveFile(uploadDir, fileName, menuImg);
		} else {
			if (menu.getImagePath().isEmpty())
				menu.setImagePath(null);
			saveMenu = menuService.save(menu, id);
		}

		return "redirect:/restaurant-page/menu";
	}

	@GetMapping("/restaurant/img")
	public String getImg(Principal p, Model model) {
		String client = p.getName();
		Integer restaurantId = memberService.myRestaurantId(client);

		Restaurant restaurant = restaurantService.getRestaurant(restaurantId);

		model.addAttribute("restaurant", restaurant);

		return "restaurantPage/restaurantImg";

	}

	@PostMapping("/restaurant/img/update")
	public String updateImg(@RequestParam(name = "file", required = false) MultipartFile restaurantImg, Model model,Principal p)
			throws IOException {
		String client = p.getName();
		Integer restaurantId = memberService.myRestaurantId(client);
		Restaurant originalRestaurant = restaurantService.getRestaurant(restaurantId);

		if (!restaurantImg.isEmpty()) {
			String fileName = StringUtils.cleanPath(restaurantImg.getOriginalFilename());
			originalRestaurant.setImg(fileName);
			Restaurant saveRestaurant = restaurantService.update(originalRestaurant);
			String uploadDir = "restaurant-photos/" + saveRestaurant.getId() + "/restaurant-Img";
			FileUploadUtil.cleanFile(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, restaurantImg);
		} else {
			if (originalRestaurant.getImg().isEmpty())
				originalRestaurant.setImg(null);
			Restaurant saveRestaurant = restaurantService.update(originalRestaurant);
		}

		return "redirect:/restaurant-page/menu";
	}

}
