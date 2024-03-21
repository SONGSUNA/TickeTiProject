package com.office.ticketreserve.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.office.ticketreserve.config.TicketDto;
import com.office.ticketreserve.productpage.PerfomanceDto;
import com.office.ticketreserve.reservation.ReservationDto;
import com.office.ticketreserve.reservation.ReservationDtoForAdmin;
import com.office.ticketreserve.reservation.ReservationService;
import com.office.ticketreserve.review.ReviewDto;
import com.office.ticketreserve.user.UserDto;
import com.office.ticketreserve.util.FileUploadService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	ReservationService reservationService;

	@GetMapping({"/", ""})
	public String admin_home(Model model) {
		log.info("[AdminController] admin_home()");
		
		String nextPage = "/admin/home";
		
		return nextPage;
	}
	
	@GetMapping("/user_management")
	public String user_management(Model model,@RequestParam(value = "size", defaultValue = "8") int size,
								 			  @RequestParam(value = "page", defaultValue = "1") int page) {
		log.info("[AdminController] user_management()");
		
		List<UserDto> userDtos = adminService.getAllUserDtoByPage(size,page);
		
	    int totalCount = adminService.getUserCount();
	    int totalPages = (int) Math.ceil((double) totalCount / size);
		
		model.addAttribute("userList", userDtos);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);
	    
	    
		String nextPage = "admin/user_management";
		
		return nextPage;
	}
	
	@PostMapping("/user_search")
	@ResponseBody
	public List<UserDto> user_search(@RequestParam("u_id") String u_id,
									 @RequestParam("u_name") String u_name,
									 @RequestParam("u_mail") String u_mail) {
		log.info("[AdminController] user_search()");
		
		List<UserDto> userDtos = adminService.getSelectUserDtos(u_id, u_name, u_mail);

		return userDtos.isEmpty() ? null : userDtos;
	}
	
	@PostMapping("/select_user_for_modify")
	@ResponseBody
	public UserDto selectUserForModify(@RequestParam("u_id") String u_id) {
		log.info("[AdminController] selectUserForModify()");
		
		UserDto userDto = adminService.selectUserByID(u_id);
			
		return userDto;
	}
	
	@PostMapping("/user_modify_confirm")
	@ResponseBody
	public String userModifyConfirm(UserDto userDto) {
		log.info("[AdminController] userModifyConfirm()");
		
		adminService.userModifyConfirm(userDto);
		
		return userDto.getU_id();
	}
	
	@GetMapping("/user_delete")
	public String user_delete_confirm(@RequestParam("u_no") int u_no) {
		log.info("[AdminController] user_delete_confirm()");
		
		adminService.userDeleteConfirm(u_no);
		
		String nextPage = "redirect:/admin/user_management";
		
		return nextPage;
	}
		
	@GetMapping("/admin_regist")
	public String admin_regist() {
		log.info("[AdminController] admin_regist()");
		
		String nextPage = "/admin/admin_regist";
			
		return nextPage;
	}
	
	@ResponseBody
	@PostMapping("/checkId")
	public boolean checkId(@RequestParam("a_id_check") String id) {
	    log.info("[AdminController] checkId()");
	    
	    return adminService.isAdmin(id);
	}
	
	@PostMapping("/admin_regist_confirm")
	public String checkId(AdminDto adminDto) {
		log.info("[AdminController] admin_regist_confirm()");
		
		int result = adminService.adminRegist(adminDto);
		
		String nextPage = "/admin/regist_success";
	
		if (result <= 0)
			nextPage = "/admin/regist_fail";
		
		return nextPage;
	}
	
	@GetMapping("/admin_management")
	
	public String admin_management(Model model) {
		log.info("[AdminController] admin_management()");
		
		String nextPage = "admin/admin_management";
		
		List<AdminDto> adminDtos = adminService.getAllAdminDtos();
		
		model.addAttribute("adminList", adminDtos);
		
		return nextPage;
		
	}
	
	@PostMapping("/admin_search")
	@ResponseBody
	public List<AdminDto> admin_search(@RequestParam("a_id")   String a_id,
									  @RequestParam("a_name") String a_name,
									  @RequestParam("a_mail") String a_mail) {
		log.info("[AdminController] user_search()");
		
		List<AdminDto> adminDtos = adminService.getSelectAdminDtos(a_id, a_name, a_mail);

		return adminDtos.isEmpty() ? null : adminDtos;
	}
	
	@PostMapping("select_admin_for_modify")
	@ResponseBody
	public AdminDto selectAdminForModify(@RequestParam("a_id") String a_id) {
		log.info("[AdminController] selectUserForModify()");
		
		AdminDto adminDto = adminService.selectAdminById(a_id);
			
		return adminDto;
	}
	
	@PostMapping("/admin_modify_confirm")
	@ResponseBody
	public String adminModifyConfirm(AdminDto adminDto, Model model) {
		log.info("[AdminController] adminModifyConfirm()");
		
		adminService.adminModifyConfirm(adminDto);
		
		return adminDto.getA_id();
	}
	
	@GetMapping("/admin_delete")
	public String admin_delete_confirm(@RequestParam("a_no") int a_no) {
		log.info("[AdminController] admin_delete_confirm()");
		
		adminService.adminDeleteConfirm(a_no);
		
		String nextPage = "redirect:/admin/admin_management";
		
		return nextPage;
	}
	
	@GetMapping("/perfomance_regist")
	public String perfomance_regist() {
		log.info("[AdminController] perfomance_regist()");
		
		String nextPage = "/admin/perfomance_regist_form";
			
		return nextPage;
	}
	
	@ResponseBody
	@PostMapping("/isPfId")
	public boolean isPfId(@RequestParam("p_id_check") String id) {
	    log.info("[AdminController] checkId()");
	    
	    return adminService.isPfId(id);
	}
	
	@PostMapping("/perfomance_regist_confirm")
	public String perfomance_regist_confirm(PerfomanceDto perfomanceDto,
											@RequestParam("thum_img") MultipartFile thumbImg,
											@RequestParam("detail_img") MultipartFile detailImg) {
		log.info("[AdminController] perfomance_regist_confirm()");
		
		String nextPage = "/admin/perfomance_regist_success";
		
		String pThumPath = fileUploadService.pThumbImgUpload(thumbImg);
		String pDetailPath = fileUploadService.pDtailImgUpload(detailImg);
		
		if (thumbImg == null || detailImg == null) {
			return "/admin/perfomance_regist_fail";
		}
		
		perfomanceDto.setP_thum("http://14.42.124.87:8091/perfomanceImg/pThum/" + pThumPath);
		perfomanceDto.setP_detail_img("[StyUrl{url='[http://14.42.124.87:8091/perfomanceImg/pDetail/" + pDetailPath + "]'}");
		
		adminService.perfomanceRegistConfirm(perfomanceDto);
		
		return nextPage;
	}
	
	@GetMapping("/ticket_management")
	public String ticketManagement(@RequestParam(value = "page", defaultValue = "1") int page,
									@RequestParam(value = "size", defaultValue = "8") int size,
						            Model model) {
		log.info("[AdminController] ticketManagement()");
		
		String nextPage = "/admin/ticket_management";
		
		List<PerfomanceDto> perfomanceDtos = adminService.getPerfomanceByPage(page, size);
		
	    int totalCount = adminService.getPerfomanceCount();
	    int totalPages = (int) Math.ceil((double) totalCount / size);
		
		model.addAttribute("perfomanceDtos", perfomanceDtos);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);
	    
		return nextPage;
	}
	
	@GetMapping("/getNoTicketPfs")
	@ResponseBody
	public List<PerfomanceDto> getNoTicketPfs() {
		log.info("[AdminController] getNoTicketPfs()");
		
		List<PerfomanceDto> noTicketPfs = adminService.getNoTicketPfs();
		
		return noTicketPfs;
	}
	
	@GetMapping("/getPerfomanceByName")
	@ResponseBody
	public List<PerfomanceDto> getPerfomanceByName(@RequestParam("p_name") String p_name) {
		log.info("[AdminController] getPerfomanceByName()");
		
		List<PerfomanceDto> noTicketPfs = adminService.getPerfomanceByName(p_name);
		
		return noTicketPfs.isEmpty() ? null : noTicketPfs;
	}
	
	@GetMapping("/getTicketInfo")
	@ResponseBody
	public TicketDto getTicketInfo(@RequestParam("p_id") String p_id, Model model) {
		log.info("[AdminController] getTicketInfo()");

		return adminService.getTicketInfo(p_id);
	}
	
	@GetMapping("/ticket_modify")
	public String ticketModify(TicketDto ticketDto) {
		log.info("[AdminController] ticketModify()");
		
		String nextPage = "/admin/ticket_modify_fail";
		
		boolean result = adminService.ticketModify(ticketDto);
		if (!result) return nextPage;
		
		result = adminService.performanceModifyByTicket(ticketDto);
		if (!result) return nextPage;
		
		nextPage = "/admin/ticket_modify_success";
		return nextPage;
	}
	
	@GetMapping("/ticket_regist")
	public String ticketRegist(TicketDto ticketDto) {
		log.info("[AdminController] ticketRegist()");
		
		String nextPage = "/admin/ticket_modify_fail";
		
		boolean result = adminService.ticketRegist(ticketDto);
		if (!result) return nextPage;
		
		result = adminService.performanceModifyByTicket(ticketDto);
		if (!result) return nextPage;
		
		nextPage = "/admin/ticket_modify_success";
		return nextPage;
	}
	
	//페이지네이션 테스트===================

	@GetMapping("/performance_modify")
	public String perfomanceModify(@RequestParam(value = "page", defaultValue = "1") int page,
									@RequestParam(value = "size", defaultValue = "6") int size,
	                               Model model) {
	    log.info("[AdminController] admin_delete_confirm()");

	    List<PerfomanceDto> perfomanceDtos = adminService.getPerfomanceByPage(page, size);
	    int totalCount = adminService.getPerfomanceCount();
	    int totalPages = (int) Math.round((double) totalCount / size);
	   
	    log.info(totalPages);
	    log.info(totalCount);
	    log.info(size);
	    
	    model.addAttribute("perfomanceDtos", perfomanceDtos);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);

	    String nextPage = "/admin/perfomance_modify";
	    return nextPage;
	}
	
	@PostMapping("/getPerformancetInfo")
	@ResponseBody
	public PerfomanceDto getPerformancetInfo(@RequestParam("p_id") String p_id) {
		log.info("[AdminController] getPerformancetInfo()");
		
		return adminService.getPerfomanceById(p_id);
	}
	
	@PostMapping("/perfomance_modify_confirm")
	public String perfomanceModifyConfirm(PerfomanceDto perfomanceDto, 
										@RequestParam("thum_img") MultipartFile thumbImg,
										@RequestParam("detail_img") MultipartFile detailImg) {
		
		String nextPage = "/admin/perfomance_modify_success";
		
		if(!thumbImg.getOriginalFilename().equals("")) {
			String pThumPath = fileUploadService.pThumbImgUpload(thumbImg);
			perfomanceDto.setP_thum("http://14.42.124.87:8091/perfomanceImg/pThum/" + pThumPath);
		}
		
		if(!detailImg.getOriginalFilename().equals("")) {
			String pDetailPath = fileUploadService.pDtailImgUpload(detailImg);
			perfomanceDto.setP_detail_img("[StyUrl{url='[http://14.42.124.87:8091/perfomanceImg/pDetail/" + pDetailPath + "]'}");
		}
		
		int result = adminService.perfomanceModifyConfirm(perfomanceDto);
		
		if (result <= 0) nextPage = "/admin/perfomance_modify_fail";
			
		return nextPage;
	}
	
	@GetMapping("/sales_state")
	public String postSalesState() {
	    log.info("[AdminController] postSalesState()");
	    
	    String nextPage = "admin/sales_state";
	    
	    return nextPage;
	}
	
	@GetMapping("/sales_state_search")
	@ResponseBody
	public Map<String, AdminChartDto> salesStateSearch(@RequestParam("stDate") String stDate,
												@RequestParam("edDate") String edDate) {
	    log.info("[AdminController] salesStateSearch()");
	    
	    Map<String, AdminChartDto> admChartDto = adminService.salesStateSearch(stDate, edDate);
	    
	    log.info("[AdminController] salesStateSearch()" + admChartDto);
	    
	    return admChartDto.isEmpty()? null : admChartDto;
	}
	
	@GetMapping("/review_management")
	public String reviewManagement(Model model,
	                               @RequestParam(value = "size", defaultValue = "6") int size,
	                               @RequestParam(value = "page", defaultValue = "1") int page,
	                               @RequestParam(value = "review_u_id", required = false) String u_id,
	                               @RequestParam(value = "review_p_name", required = false) String p_name) {
	    log.info("[AdminController] reviewManagement()");
	    
	    List<ReviewDto> searchReviewDto = adminService.searchReview(u_id, p_name, page, size);
	    int totalCount = adminService.getTotalReviewCount(u_id, p_name);
	    int totalPages = (int) Math.ceil((double) totalCount / size);
	    
	    model.addAttribute("searchReviewDto", searchReviewDto);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);
	    
	    return "admin/review_management";
	}

	@ResponseBody
	@PostMapping("/review_delete")
	public int reviewDelete(@RequestParam("rv_no") int rv_no) {
	    
		log.info("[AdminController] reviewDelete()");
	    
		int result = -1;
	   
		result = adminService.adminReviewDelete(rv_no);
	    
		return result;
	}

	@GetMapping("/review_search")
	@ResponseBody
	public List<ReviewDto> reviewSearch(@RequestParam(value = "review_u_id", required = false) String u_id,
	                                     @RequestParam(value = "review_p_name", required = false) String p_name,
	                                     @RequestParam(value = "page", defaultValue = "1") int page,
	                                     @RequestParam(value = "size", defaultValue = "6") int size) {
	    return adminService.searchReview(u_id, p_name, page, size);
	}

	@GetMapping("/review_count")
	@ResponseBody
	public int reviewCount(@RequestParam(value = "review_u_id", required = false) String u_id,
	                       @RequestParam(value = "review_p_name", required = false) String p_name) {
	    return adminService.getReviewCount(u_id, p_name);
	}
	
   @GetMapping("/ticket_state")
   public String ticketState (@RequestParam(value = "page", defaultValue = "1") int page,
							  @RequestParam(value = "size", defaultValue = "8") int size,
				              Model model) {
      log.info("[AdminController] ticketState()");
      
      List<ReservationDto> reservationDtos = adminService.gerReservationByPage(page, size);
	    int totalCount = adminService.getReservationCount();
	    int totalPages = (int) Math.ceil((double) totalCount / size);

	    model.addAttribute("reservationDtos", reservationDtos);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);
      
      String nextPage = "/admin/ticket_state";
         
      return nextPage;
   }
   
   @GetMapping("/getReservationByName")
   @ResponseBody
   public List<ReservationDtoForAdmin> getReservationByName(@RequestParam("p_name") String p_name) {
		log.info("[AdminController] getReservationByName()");
		
		List<ReservationDtoForAdmin> noTicketPfs = adminService.getReservationByName(p_name);
		
		return noTicketPfs.isEmpty() ? null : noTicketPfs;
	}
   
   @GetMapping("/ticket_cancel_confirm")
   @ResponseBody
   public String ticketCancelConfirm(@RequestParam("r_no") int r_no) {
	   log.info("[AdminController] ticketCancelConfirm()");
	   
	   int t_no = reservationService.getTNoByRNo(r_no);
	   reservationService.cancelConfirm(r_no, t_no);
	   
	   return "Success";
   }
   
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		log.info("[AdminController] logout()");
		
		session.removeAttribute("loginedAdminDto");
		
		String nextPage = "redirect:/";
		
		return nextPage;
	}
	
	
}