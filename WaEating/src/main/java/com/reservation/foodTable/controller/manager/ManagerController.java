package com.reservation.foodTable.controller.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reservation.foodTable.Service.AreaService;
import com.reservation.foodTable.Service.CategoryService;
import com.reservation.foodTable.Service.CustomerSupportService;
import com.reservation.foodTable.Service.MemberService;
import com.reservation.foodTable.Service.MenuService;
import com.reservation.foodTable.Service.RestaurantCategoryService;
import com.reservation.foodTable.Service.RestaurantDefaultTimeService;
import com.reservation.foodTable.Service.RestaurantService;
import com.reservation.foodTable.config.FileUploadUtil;
import com.reservation.foodTable.dto.FAQDTO;
import com.reservation.foodTable.dto.NoticeDTO;
import com.reservation.foodTable.dto.RestaurantEditDTO;
import com.reservation.foodTable.embeddedType.BusinessHour;
import com.reservation.foodTable.embeddedType.Score;
import com.reservation.foodTable.entity.Banner;
import com.reservation.foodTable.entity.Category;
import com.reservation.foodTable.entity.Member;
import com.reservation.foodTable.entity.Menu;
import com.reservation.foodTable.entity.MenuCategory;
import com.reservation.foodTable.entity.OneToOneInquiry;
import com.reservation.foodTable.entity.OneToOneInquiryComment;
import com.reservation.foodTable.entity.Restaurant;
import com.reservation.foodTable.entity.RestaurantDefaultTime;
import com.reservation.foodTable.enumClass.InquiryState;
import com.reservation.foodTable.enumClass.PriceRange;
import com.reservation.foodTable.enumClass.Role;

@Controller
public class ManagerController {
   
   private final CustomerSupportService customerSupportService;
   private final RestaurantService restaurantService;
   private final AreaService areaService;
   private final RestaurantDefaultTimeService restaurantDefaultTimeService;
   private final MenuService menuService;
   private final MemberService memberService;
   private final RestaurantCategoryService restaurantCategoryService;
   private final CategoryService categoryService;
   
	public ManagerController(RestaurantCategoryService restaurantCategoryService,
			CustomerSupportService customerSupportService, RestaurantService restaurantService, AreaService areaService,
			RestaurantDefaultTimeService restaurantDefaultTimeService, MenuService menuService,
			MemberService memberService, CategoryService categoryService) {

		this.customerSupportService = customerSupportService;
		this.restaurantService = restaurantService;
		this.areaService = areaService;
		this.restaurantDefaultTimeService = restaurantDefaultTimeService;
		this.menuService = menuService;
		this.memberService = memberService;
		this.restaurantCategoryService = restaurantCategoryService;
		this.categoryService = categoryService;
	}
   
   @GetMapping("/manager")
   public String home() {
	   
      return "redirect:/manager/restaurant";
   }
   
   @GetMapping("/manager/faq")
   public String FAQHome(Model model,@PageableDefault(size =5,page=0,sort = "id", direction = Sort.Direction.DESC)Pageable pageable
         ,@RequestParam(required = false, defaultValue = "") String keyword) {
      
      model.addAttribute("FAQ",customerSupportService.findFAQ(keyword,pageable));
      model.addAttribute("keyword", keyword);
      
      return "manager/FAQ";
   }
   
   @GetMapping("/manager/faq/createForm")
   public String FAQHome(Model model) {
      
      model.addAttribute("FAQ", new FAQDTO());
      model.addAttribute("category",customerSupportService.getCategories());
      model.addAttribute("title","신규 FAQ 등록");
      
      return "manager/createFAQForm";
   }
   
   @PostMapping("/manager/faq/save")
   public String FAQSave(@ModelAttribute("FAQ")FAQDTO faqDTO,@RequestParam("category")int categoryId) {
      
      System.out.println("FAQDTO : "+faqDTO);
      System.out.println("Category-Id : "+categoryId);
      customerSupportService.saveFAQ(faqDTO,categoryId);
      return "redirect:/manager/faq";
   }
   
   @GetMapping("/manager/faq/updateForm/{id}")
   public String FAQupdateForm(@PathVariable("id")int FAQId,Model model) {
      FAQDTO faqDto = customerSupportService.findFAQDTOByFAQId(FAQId);
      model.addAttribute("FAQ", faqDto);
      model.addAttribute("category",customerSupportService.getCategories());
      model.addAttribute("orginalCategoryName",faqDto.getCategoryName());
      model.addAttribute("title","기존 FAQ 수정");
      
      return "manager/createFAQForm";
   }
   
   @PostMapping("/manager/faq/delete/{id}")
   public String FAQdelete(@PathVariable("id")int FAQId) {
      System.out.println("delete Id : "+FAQId);
      customerSupportService.deleteFAQ(FAQId);
      return "redirect:/manager/faq";
   }
   
   
   @GetMapping("/manager/notice")
   public String getNotices(Model model,@PageableDefault(size=5,page=0,sort = "id", direction = Sort.Direction.DESC)Pageable pageable
         ,@RequestParam(required = false, defaultValue = "") String keyword
         ,@RequestParam(name="target",required = false, defaultValue = "1")Integer target) {
      
      model.addAttribute("Notice",customerSupportService.getNoticeList(keyword,target,pageable));
      model.addAttribute("keyword", keyword);
      model.addAttribute("target", target);

      return "manager/notice";
   }
   
   @GetMapping("/manager/notice/{id}")
   public String getNotices(Model model,@PathVariable("id")Integer noticeId) {
      
      model.addAttribute("notice",customerSupportService.getNoticePageById(noticeId));
      model.addAttribute("head",2);

      return "manager/notice_detail";
   }
   
   @GetMapping("/manager/notice/createForm")
   public String NoticeCreateForm(Model model) {

      model.addAttribute("notice",new NoticeDTO());
      model.addAttribute("title","신규 공지사항 등록");
      return "manager/createNoticeForm";
   }

   @GetMapping("/manager/notice/updateForm/{id}")
   public String NoticeUpdateForm(@PathVariable("id")int noticeId,Model model) {
      

      model.addAttribute("notice",customerSupportService.findNoticeDTOByNoticeId(noticeId));
      model.addAttribute("title","기존 공지사항 수정");
      return "manager/createNoticeForm";
   }
   
   @PostMapping("/manager/notice/save")
   public String saveNotice(NoticeDTO noticeDTO) {
      System.out.println(noticeDTO);
      customerSupportService.saveOrUpdateNotice(noticeDTO);
      return "redirect:/manager/notice";
   }
   
   @PostMapping("/manager/notice/delete/{id}")
   public String Noticedelete(@PathVariable("id")int noticeId) {

      customerSupportService.deleteNotice(noticeId);
      return "redirect:/manager/notice";
   }
   
   //태식 - 유저들 문의 조회
   @GetMapping("/manager/oneToOne")
   public String getOneToOnes(Model model,@PageableDefault(size=5,page=0,sort = "id", direction = Sort.Direction.ASC)Pageable pageable
         ,@RequestParam(name="keyword",required = false, defaultValue = "") String keyword
         ,@RequestParam(name="target",required = false, defaultValue = "1")Integer target) {
      
      model.addAttribute("OneToOne",customerSupportService.getOneToOneList(keyword,target,pageable));
      model.addAttribute("keyword", keyword);
      model.addAttribute("target", target);
      
      return "manager/oneToOne";
   }
   //태식 - 문의 자세히 보기, 답변달기, 수정하기
   @GetMapping("/manager/oneToOne/{id}")
   public String getOneToOnes(Model model,@PathVariable("id")Integer oneToOneId) {
      model.addAttribute("OneToOne",customerSupportService.getOneToOnePageById(oneToOneId));
      OneToOneInquiryComment com = customerSupportService.findOneToOneInquiryId(oneToOneId);
      if(com == null) {
         OneToOneInquiryComment one = new OneToOneInquiryComment();
         OneToOneInquiry in = customerSupportService.findById(oneToOneId);
         one.setOneToOneInquiry(in);
         model.addAttribute("comment", one);
      }else {
         model.addAttribute("comment", com);
      }

      return "manager/oneToOne_detail";
   }
   //태식 - 문의 답변 save 하고 답변 상태 바꾸기
   @PostMapping("/manager/oneToOneComment/save")
   public String saveComment(OneToOneInquiryComment comment, Model theModel) {
      customerSupportService.saveComment(comment);
      
      int inquiryId = comment.getOneToOneInquiry().getId();
      OneToOneInquiry in = customerSupportService.findById(inquiryId);
      in.setState(InquiryState.COMPLETED);
      customerSupportService.save(in);
      
      return "redirect:/manager/oneToOne";
   }
   
   /****************************************************** 오세종 ****************************************************************************************/
   @GetMapping("manager/restaurant")
	public String restaurantList(Model model,@PageableDefault(size=5,page=0,sort = "id", direction = Sort.Direction.ASC)Pageable pageable
	         ,@RequestParam(name="keyword",required = false, defaultValue = "") String keyword
	         ,@RequestParam(name="target",required = false, defaultValue = "1")Integer target) {
		model.addAttribute("restaurantList",restaurantService.listByPage(keyword,target,pageable));
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("target", target);
		
		return "manager/restaurantList";
	}

	@GetMapping("/manager/restaurantRegister")
	public String registerRestaurant(Model model) {
		List<String> times = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			times.add(String.format("%02d:00", i));
		}
		RestaurantEditDTO restaurant = new RestaurantEditDTO();
		model.addAttribute("restaurantEdit", restaurant);
		model.addAttribute("defaultTime", times);
		return "manager/restaurantEdit";
	}

	@PostMapping("/manager/save")
	public String editRestaurant(@RequestParam(name = "file", required = false) MultipartFile restaurantImg,
			@ModelAttribute("restaurantEdit") RestaurantEditDTO restaurantEdit,
			@RequestParam(name = "time[]", required = false) String[] times,
			@RequestParam(name = "area[]") Integer[] area, Model model) throws IOException {
		restaurantEdit.restaurant.setSi(areaService.get(area[0]));
		restaurantEdit.restaurant.setGun(areaService.get(area[1]));
		restaurantEdit.restaurant.setDong(areaService.get(area[2]));
		restaurantEdit.restaurant.setBusinessHour(new BusinessHour(restaurantEdit.getOpenTime(),restaurantEdit.getCloseTime() ));
		restaurantEdit.restaurant.setPriceRange(PriceRange.valueOf(restaurantEdit.getPriceRange()));
		restaurantEdit.restaurant.setScore(new Score());
		Category category = categoryService.getCategory(restaurantEdit.getCategory());

		if (!restaurantImg.isEmpty()) {
			String fileName = StringUtils.cleanPath(restaurantImg.getOriginalFilename());
			restaurantEdit.restaurant.setImg(fileName);
			Restaurant saveRestaurant = restaurantService.save(restaurantEdit.restaurant, times);
			restaurantCategoryService.save(category, saveRestaurant);
			String uploadDir = "restaurant-photos/"+saveRestaurant.getId()+"/restaurant-Img";
			FileUploadUtil.cleanFile(uploadDir);
			FileUploadUtil.saveFile(uploadDir,fileName,restaurantImg);
		} else {
			if (restaurantEdit.restaurant.getImg().isEmpty())
				restaurantEdit.restaurant.setImg(null);
			Restaurant saveRestaurant = restaurantService.save(restaurantEdit.restaurant, times);
			restaurantCategoryService.save(category, saveRestaurant);
		}
		return "redirect:/manager/restaurant";
	}

	@PostMapping("/manager/update")
	public String updateRestaurant(@RequestParam(name = "file", required = false) MultipartFile restaurantImg,
			@ModelAttribute("restaurantEdit") RestaurantEditDTO restaurantEdit,
			@RequestParam(name = "time[]", required = false) String[] times,
			Model model) throws IOException {
		Arrays.asList(times).forEach(System.err::println);
		Restaurant originalRestaurant = restaurantService.getRestaurant(restaurantEdit.restaurant.getId());
		
		restaurantEdit.restaurant.setDong(originalRestaurant.getDong());
		restaurantEdit.restaurant.setSi(originalRestaurant.getSi());
		restaurantEdit.restaurant.setGun(originalRestaurant.getGun());
		restaurantEdit.restaurant.setSpecificArea(originalRestaurant.getSpecificArea());
		restaurantEdit.restaurant.setBusinessHour(new BusinessHour(restaurantEdit.getOpenTime(),restaurantEdit.getCloseTime() ));
		restaurantEdit.restaurant.setPriceRange(PriceRange.valueOf(restaurantEdit.getPriceRange()));
		restaurantEdit.restaurant.setScore(new Score());
		Category category = categoryService.getCategory(restaurantEdit.getCategory());

		if (!restaurantImg.isEmpty()) {
			String fileName = StringUtils.cleanPath(restaurantImg.getOriginalFilename());
			restaurantEdit.restaurant.setImg(fileName);
			Restaurant saveRestaurant = restaurantService.update(restaurantEdit.restaurant,times);
			restaurantCategoryService.update(category, saveRestaurant);
			String uploadDir = "restaurant-photos/" + saveRestaurant.getId() + "/restaurant-Img";
			FileUploadUtil.cleanFile(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, restaurantImg);
		} else {
			if (restaurantEdit.restaurant.getImg().isEmpty())
				restaurantEdit.restaurant.setImg(null);
			Restaurant saveRestaurant = restaurantService.update(restaurantEdit.restaurant, times);
			restaurantCategoryService.update(category, saveRestaurant);
		}

		return "redirect:/manager/restaurant";

	}

	@GetMapping("/manager/edit/{id}")
	public String editRestaurant(@PathVariable("id") Integer id, Model model) {
		Restaurant restaurant = restaurantService.getRestaurant(id);
		List<RestaurantDefaultTime> defaultTime = restaurantDefaultTimeService.gettime(restaurant);
		RestaurantEditDTO restaurantEditDTO = new RestaurantEditDTO(restaurant.getOpenTime(), restaurant.getCloseTime(),
				restaurant.getPriceRange(), restaurant, defaultTime);
		restaurantEditDTO.setCategory(restaurantCategoryService.get(id));
		List<String> times = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			times.add(String.format("%02d:00", i));
		}
		
		List<String> myTimes = defaultTime.stream().map(time->{
			return  time.getTime().toString();
		}).toList();
		model.addAttribute("myTimes",myTimes);
		model.addAttribute("restaurantEdit", restaurantEditDTO);
		model.addAttribute("defaultTime", times);
		model.addAttribute("address", restaurantEditDTO.restaurant.getAdrress());
		return "manager/restaurantEdit";
	}

	@GetMapping("/manager/menu/list/{id}")
	public String listMenu(@PathVariable("id") Integer id, Model model) {
		List<Menu> menuList = menuService.getMenus(id);
		String restaurantName = restaurantService.getRestaurantName(id);
		model.addAttribute("menus", menuList);
		model.addAttribute("restaurantId",id);
		model.addAttribute("restaurantName",restaurantName);
		return "manager/menuList";
	}
	@PostMapping("/manager/menu/represent/{restaurantId}")
	public String representMenu(@PathVariable("restaurantId")Integer restaurantId,Model model,@RequestParam("menu[]")List<Integer> menuIds) {
		
		menuService.menuRepresentRegister(restaurantId,menuIds);
		System.err.println("menuIds : "+menuIds);
		System.err.println("restaurantId : "+restaurantId);
		return "redirect:/manager/menu/list/"+restaurantId;
	}

	@GetMapping("manager/menu/add/{id}")
	public String addMenu(@PathVariable("id") Integer id, Model model) {
		Menu menu = new Menu();

		model.addAttribute("menu", menu);
		model.addAttribute("restaurantId",id);
		model.addAttribute("categoryList",restaurantService.myMenuCategory(id));
		return "manager/menuEdit";
	}

	@PostMapping("/manager/menu/save")
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
//		redirectAttributes.addFlashAttribute("id",id);
		return "redirect:/manager/menu/list/"+id;
	}

	@GetMapping("manager/menu/edit/{id}")
	public String editMenu(@PathVariable("id") Integer id, Model model) {
		Menu menu = menuService.getMenu(id);
		model.addAttribute("menu", menu);
		model.addAttribute("restaurantId", menu.getRestaurant().getId());
		model.addAttribute("categoryList",restaurantService.myMenuCategory(menu.getRestaurant().getId()));
		return "manager/menuEdit";
	}

	@GetMapping("manager/menu/delete/{id}")
	public String deleteMenu(@PathVariable("id") Integer id, Model model) {
		Menu menu = menuService.getMenu(id);
		Integer restaurantId=  menu.getRestaurant().getId();
		menuService.deleteMenu(id);

		return "redirect:/manager/menu/list/"+id;
	}

	@GetMapping("manager/join/owner/{id}")
	public String joinOwner(@PathVariable("id") Integer id, Model model) {
		Member owner = new Member();
		model.addAttribute("restaurantId", id);
		model.addAttribute("owner", owner);
		return "manager/createOwner";
	}

	@GetMapping("manager/edit/owner/{id}")
	public String editOwner(@PathVariable("id") Integer id, Model model) {
		Member owner = memberService.findById(id);
		model.addAttribute("owner", owner);
		model.addAttribute("restaurantId", owner.getRestaurant().getId());
		return "manager/createOwner";
	}

	@PostMapping("manager/add/owner/{id}")
	public String addOwner(@ModelAttribute("owner") Member owner, @PathVariable("id") Integer id, Model model) {
		System.out.println("askdjaskdjsk");
		owner.setRole(Role.ROLE_OWNER);
		owner.setRestaurant(restaurantService.getRestaurant(id));
		memberService.save(owner);

		return "redirect:/manager/restaurant";
	}
	
	
	   /////////////////////////하지원/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	   @GetMapping("/manager/banner")
	   public String banner(Model model) {
	      Banner banner = new Banner();
	      model.addAttribute("banner", banner);
	      List<Banner> bannerList = customerSupportService.findAllBanner();
	      model.addAttribute("bannerList", bannerList);
	      return "/manager/banner";
	   }

	   @PostMapping("/manager/banner/save")
	   public String bannerSave(Banner banner, @RequestParam("image") MultipartFile multipartFile, Model model)
	         throws IOException {
	      if (!multipartFile.isEmpty()) {
	         String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	         banner.setPhotos(fileName);
	         System.out.println("0번"+banner.getId() + banner.getPhotos()); 
	         Banner savedUser = customerSupportService.saveBanner(banner);
	         String uploadDir = "banner/" + savedUser.getId();
				/* FileUploadUtil.cleanDir(uploadDir); */
	         FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
	         System.out.println("1번"+banner.getId() + banner.getPhotos()); 
	      } else {
	         if (banner.getPhotos().isEmpty())
	            banner.setPhotos(null);
	         customerSupportService.saveBanner(banner);
	         System.out.println("2번"+banner.getId() + banner.getPhotos());
	      }

	      return "redirect:/manager/banner";
	   }
	   
	   @GetMapping("/manager/banner/delete/{id}")
	   public String bannerDelete(Model model, @PathVariable("id") int id) {
		  System.out.println("배너아이디"+id);
	      customerSupportService.deleteBannerById(id);
	      System.out.println("배너아이디"+id);
	      return "redirect:/manager/banner";
	   }

	   @GetMapping("/manager/banner/new")
	   public String bannerCreate() {
	      Banner banner = new Banner();
	      banner.setPhotos(null);
	      customerSupportService.saveBanner(banner);
	      return "redirect:/manager/banner";
	   }
	   
	   @GetMapping("/manager/menuCategory/list/{id}")
	   public String menuCategory(Model model, @PathVariable("id") int id) {
		   
		   
		   model.addAttribute("categoryForm",new MenuCategory());
		   // 카테고리로 부터 있는거 가져오기
		   List<MenuCategory> categoryList = restaurantService.myMenuCategory(id);
		   model.addAttribute("categoryList",categoryList);
		   model.addAttribute("restaurantId",id);
		   model.addAttribute("orderNumber", categoryList.isEmpty()?1:categoryList.get(categoryList.size()-1).getOrderNumber()+1);
		   
		   return "/manager/menuCategory";
	   }
	   
	   @PostMapping("/manager/menuCategory/save")
	   public String menuCategorySave(@RequestParam("name")String name,@RequestParam("orderNumber")Integer orderNumber,@RequestParam("restaurantId")Integer restaurantId) {
		   
		   restaurantService.saveMenuCategory(name,orderNumber,restaurantId);
		   
		   return "redirect:/manager/menuCategory/list/"+restaurantId;
	   }
	   
	   @PostMapping("/manager/menuCategory/order/save")
	   public String menuCategoryOrderSave(@RequestParam("menuIds[]")List<Integer> menuIds,@RequestParam("restaurantId")Integer restaurantId) {
		   
		   menuIds.forEach(System.err::println);
		   restaurantService.categoryOrderUpdate(menuIds);
		   return"redirect:/manager/menuCategory/list/"+restaurantId;
	   }
	   
	   //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	 
}