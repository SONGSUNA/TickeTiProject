package com.office.ticketreserve.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.office.ticketreserve.config.TicketDto;
import com.office.ticketreserve.productpage.PerfomanceDto;
import com.office.ticketreserve.reservation.ReservationDao;
import com.office.ticketreserve.reservation.ReservationDto;
import com.office.ticketreserve.reservation.ReservationDtoForAdmin;
import com.office.ticketreserve.review.ReviewDto;
import com.office.ticketreserve.user.IUserDaoForMybatis;
import com.office.ticketreserve.user.UserDao;
import com.office.ticketreserve.user.UserDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminService {
	
	@Autowired
	AdminDaoForMyBatis adminDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	IUserDaoForMybatis userDao;
	
	@Autowired
	UserDao userDaoNoBatis;
	
	@Autowired
	AdminChartDto adminChartDto;
	
	@Autowired
	ReservationDao reservationDao;

	public List<UserDto> getAllUserDtoByPage(int size,int page) {
	      log.info("[AdminService] getAllUserDto()");
	      int offset = (page - 1) * size;
	      List<UserDto> userDtos = adminDao.selectAllUsers(offset,size);
	      
	      return userDtos;
	   }
	
    public int getUserCount() {
      return adminDao.selectUserCount();
    }
	
	public List<UserDto> getSelectUserDtos(String u_id, String u_name, String u_mail) {
		log.info("[AdminService] getSelectUserDtos()");
		
		List<UserDto> userDtos = null;
		
		if(u_id != "") {
			userDtos = adminDao.selectUsersById(u_id);
			return userDtos;
		}
		else if (u_name != "") {
			userDtos = adminDao.selectUsersByName(u_name);
			return userDtos;
		}
		else
			userDtos = adminDao.selectUsersByMail(u_mail);
			return userDtos;
	}

	public UserDto selectUserByID(String u_id) {
		log.info("[AdminService] selectUserByID()");
		
		return adminDao.selectUsersById(u_id).get(0);
	}

	public void userModifyConfirm(UserDto userDto) {
		log.info("[AdminService] userModifyConfirm()");
		
		adminDao.updateUserWithoutPw(userDto);
	}

	public void userDeleteConfirm(int u_no) {
		log.info("[AdminService] userDeleteConfirm()");
		
		userDao.deleteUser(u_no);
	}

	public boolean isAdmin(String adminId) {
		log.info("[AdminService] isAdmin()");
		
		boolean result = true;
		
		AdminDto adminDto = adminDao.selectAdminById(adminId);
		if (adminDto != null) return result;
		if (userDaoNoBatis.isUser(adminId)) return result;
		
		return false;
	}

	public int adminRegist(AdminDto adminDto) {
		log.info("[AdminService] adminRegist()");
		
		AdminDto checkAdminDto = adminDao.selectAdminById(adminDto.getA_id());
		if(checkAdminDto != null) return -99;
		
		String encodePw = passwordEncoder.encode(adminDto.getA_pw());
		adminDto.setA_pw(encodePw);
		
		return adminDao.insertAdmin(adminDto);
	}

	public List<AdminDto> getAllAdminDtos() {
		log.info("[AdminService] adminRegist()");
		
		return adminDao.selectAllAdmins();
	}

	public List<AdminDto> getSelectAdminDtos(String a_id, String a_name, String a_mail) {
		log.info("[AdminService] getSelectAdminDtos()");
		
		List<AdminDto> adminDtos = null;
		
		if(a_id != "") {
			adminDtos = adminDao.selectAdminsById(a_id);
			return adminDtos;
		}
		else if (a_name != "") {
			adminDtos = adminDao.selectAdminsByName(a_name);
			return adminDtos;
		}
		else
			adminDtos = adminDao.selectAdminsByMail(a_mail);
			return adminDtos;
	}

	public AdminDto selectAdminById(String a_id) {
		log.info("[AdminService] selectAdminById()");
	
		return adminDao.selectAdminById(a_id);
	}

	public void adminModifyConfirm(AdminDto adminDto) {
		log.info("[AdminService] adminModifyConfirm()");
		
		adminDao.updateAdminWitoutPw(adminDto);
	}

	public void adminDeleteConfirm(int a_no) {
		log.info("[AdminService] getSelectAdminDtos()");
		
		adminDao.deleteAdmin(a_no);
	}

	public boolean isPfId(String id) {
		log.info("[AdminService] isPfId()");
		
		PerfomanceDto perfomanceDto = adminDao.selectPerfomanceById(id);
		if (perfomanceDto != null) return true;
		
		return false;
	}

	public void perfomanceRegistConfirm(PerfomanceDto perfomanceDto) {
		log.info("[AdminService] perfomanceRegistConfirm()");
		
		perfomanceDto.setP_start_date(perfomanceDto.getP_start_date().replaceAll("-", "."));
		perfomanceDto.setP_end_date(perfomanceDto.getP_end_date().replaceAll("-", "."));
		
		if (perfomanceDto.getP_detail_cautions() != null)
			adminDao.insertPerfomance(perfomanceDto);
		else
			adminDao.insertPerfomanceNotDetailCautions(perfomanceDto);
	}

	public List<PerfomanceDto> getAllPerfomance() {
		log.info("[AdminService] getAllPerfomance()");
		
		return adminDao.selectAllPerfomance();
	}
	//페이지네이션 테스트===================
	public List<PerfomanceDto> getPerfomanceByPage(int page, int size) {
	    log.info("[AdminService] getPerfomanceByPage()");
	    int offset = (page - 1) * size;
	    return adminDao.selectPerfomanceByPage(offset, size);
	}

	public List<ReservationDto> gerReservationByPage(int page, int size) {
		log.info("[AdminService] gerReservationByPage()");
	    int offset = (page - 1) * size;
	    return adminDao.selectReservationeByPage(offset, size);
	}
	
	
	public int getPerfomanceCount() {
	    log.info("[AdminService] getPerfomanceCount()");
	    return adminDao.selectPerfomanceCount();
	}
	//페이지네이션 테스트===================
	
	public int getReservationCount() {
		log.info("[AdminService] getReservationCount()");
		return adminDao.selectReservationCount();
	}

	public List<PerfomanceDto> getNoTicketPfs() {
		log.info("[AdminService] getNoTicketPfs()");
		
		return adminDao.selectAllPerfomanceNoTicket();
	}

	public List<PerfomanceDto> getPerfomanceByName(String p_name) {
		log.info("[AdminService] getPerfomanceByName()");
		
		return adminDao.selectAllPerfomanceByName(p_name);
	}

	public TicketDto getTicketInfo(String p_id) {
		log.info("[AdminService] getTicketInfo()");
		
		return adminDao.selectTicketByPId(p_id);
	}

	public boolean ticketModify(TicketDto ticketDto) {
		log.info("[AdminService] ticketModify()");
		
		ticketDto.setT_p_date("[" + ticketDto.getT_p_date() + "]");
		
		int result = adminDao.updateTicket(ticketDto);
		
		return result > 0 ? true : false;
	}

	public boolean ticketRegist(TicketDto ticketDto) {
		log.info("[AdminService] ticketRegist()");
		
		ticketDto.setT_p_date("[" + ticketDto.getT_p_date() + "]");
		
		int result = adminDao.insertTicket(ticketDto);
		
		return result > 0 ? true : false;
	}
	public boolean performanceModifyByTicket(TicketDto ticketDto) {
		log.info("[AdminService] performanceModifyByTicket()");
		
		String p_ticket = ticketDto.getT_seattype_1() + " " + addCommas(ticketDto.getT_price_1()) + "원";
		
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + ticketDto.getT_seattype_2());
		
		if (!ticketDto.getT_seattype_2().equals("null"))
			p_ticket += ", " + ticketDto.getT_seattype_2() + " " + addCommas(ticketDto.getT_price_2()) + "원";
		if (!ticketDto.getT_seattype_3().equals("null"))
			p_ticket += ", " + ticketDto.getT_seattype_3() + " " + addCommas(ticketDto.getT_price_3()) + "원";
		if (!ticketDto.getT_seattype_4().equals("null"))
			p_ticket += ", " + ticketDto.getT_seattype_4() + " " + addCommas(ticketDto.getT_price_4()) + "원";
		if (!ticketDto.getT_seattype_5().equals("null"))
			p_ticket += ", " + ticketDto.getT_seattype_5() + " " + addCommas(ticketDto.getT_price_5()) + "원";
		if (!ticketDto.getT_seattype_6().equals("null"))
			p_ticket += ", " + ticketDto.getT_seattype_6() + " " + addCommas(ticketDto.getT_price_6()) + "원";
		
		String p_time = formatString(ticketDto.getT_p_date());
		
		return adminDao.updatePerformanceTicket(p_ticket, p_time, ticketDto.getP_id());
	}
	
	 public PerfomanceDto getPerfomanceById(String p_id) {
		 log.info("[AdminService] getPerfomanceById()");
		 
		return adminDao.selectPerfomanceById(p_id);
	}
	 
	public int perfomanceModifyConfirm(PerfomanceDto perfomanceDto) {
		log.info("[AdminService] perfomanceModifyConfirm()");
		
		int result;
		
		perfomanceDto.setP_start_date(perfomanceDto.getP_start_date().replaceAll("-", "."));
		perfomanceDto.setP_end_date(perfomanceDto.getP_end_date().replaceAll("-", "."));
		
		if (perfomanceDto.getP_thum() == null && 
			perfomanceDto.getP_detail_img() == null)
				result = adminDao.updatePfWithoutImg(perfomanceDto);
		
		else if (perfomanceDto.getP_detail_img() == null)
				result = adminDao.updatePfWithThumb(perfomanceDto);
		
		else if (perfomanceDto.getP_thum() == null)
				result = adminDao.updatePfWithDetailImg(perfomanceDto);
		
		else
				result = adminDao.updatePfWithImg(perfomanceDto);
		
		return result;
	}
	
	public Map<String, AdminChartDto> salesStateSearch(String stDate, String edDate) {
		log.info("[AdminController] salesStateSearch()");
		
		List<ReservationDto> rsvDto = adminDao.selectRsvInfo(stDate, edDate);
		List<AdminChartDto> searchResult = new ArrayList<>();

		searchResult = returnAdminChartDtos(rsvDto);
					
		Map<String, AdminChartDto> salesByDate = new HashMap<>();
		for(int i = 0; i<searchResult.size(); i++) {
			String date = searchResult.get(i).getR_reg_date();
			
			if(!salesByDate.containsKey(date)) {
				AdminChartDto dto= new AdminChartDto();
				dto.setR_reg_date(date);
				salesByDate.put(date, dto);
			}
			
			salesByDate.get(date).setDaySales(salesByDate.get(date).getDaySales() + searchResult.get(i).getR_price());
			
			switch (searchResult.get(i).getP_category()) {
				case "대중음악":
					salesByDate.get(date).setConcertSales(salesByDate.get(date).getConcertSales() + searchResult.get(i).getR_price());
					break;
				case "연극":
					salesByDate.get(date).setTheaterSales(salesByDate.get(date).getTheaterSales() + searchResult.get(i).getR_price());
					break;
				case "서양음악(클래식)":
					salesByDate.get(date).setClassicSales(salesByDate.get(date).getClassicSales() + searchResult.get(i).getR_price());
					break;
				case "뮤지컬":
					salesByDate.get(date).setMusicalSales(salesByDate.get(date).getMusicalSales() + searchResult.get(i).getR_price());
					break;
				case "한국음악(국악)":
					salesByDate.get(date).setKoreanMusicSales(salesByDate.get(date).getKoreanMusicSales() + searchResult.get(i).getR_price());
					break;
			}
		}
		
		Map<String, AdminChartDto> sortedSalesByDate = salesByDate.entrySet().stream()
	            .sorted(Map.Entry.comparingByKey())
	            .collect(Collectors.toMap(
	                    Map.Entry::getKey,
	                    Map.Entry::getValue,
	                    (oldValue, newValue) -> oldValue,
	                    LinkedHashMap::new
	            ));
		
		return sortedSalesByDate;
	}
	
	
	
	public List<ReservationDtoForAdmin> getReservationByName(String p_name) {
		log.info("[AdminController] getReservationByName()");
		
		return adminDao.selectAllReservationByName(p_name);
	}

	private List<AdminChartDto> returnAdminChartDtos(List<ReservationDto> rsvDto) {
		
		List<AdminChartDto> searchResult = new ArrayList<>();
		
		for(int i = 0; i<rsvDto.size(); i++) {
			int tNo = rsvDto.get(i).getT_no();
			String pId = adminDao.selectTicket(tNo).getP_id();			
			PerfomanceDto perfoDto = adminDao.selectPerfomanceById(pId);
			
			AdminChartDto dto= new AdminChartDto();
			dto.setR_price(rsvDto.get(i).getR_price());
			dto.setP_category(perfoDto.getP_category());
			dto.setR_reg_date(rsvDto.get(i).getR_reg_date());

			searchResult.add(dto);
		}
		return searchResult;
	}

	// 유틸 --------------------------------------------------------------------------------------------------------------------------------------

	private String formatString(String input) {
        String[] elements = input.split(", ");
        StringBuilder result = new StringBuilder();

        for (String element : elements) {
            String day = element.replaceAll("[^가-힣]", "");
            String time = element.replaceAll("[^0-9:]", "");

            if (result.length() > 0) {
                result.append(", ");
            }

            result.append(day);

            if (day.equals("토요일") || day.equals("일요일") || day.equals("HOL")) {
                result.append("(").append(time).append(")");
            } else {
                result.append(time);
            }
        }

        return result.toString();
	 }
	 
	 
	private String addCommas(int number) {
		String str = Integer.toString(number);
		
		String result = "";while (str.length() > 3) {
			result = "," + str.substring(str.length() - 3) + result;
			str = str.substring(0, str.length() - 3);
		}
		result = str + result;
		
		return result;
	}
// 리뷰관리----------------------------------------------------------------------------

	public int adminReviewDelete(int rv_no) {
		
		return adminDao.deleteReviewByRv_no(rv_no);
	}

	
	public List<ReviewDto> searchReview(String u_id, String p_name, int page, int size) {
	    int offset = (page - 1) * size;
	    return adminDao.selectReviewBySearch(u_id, p_name, offset, size);
	}

	public int getTotalReviewCount(String u_id, String p_name) {
	    return adminDao.selectTotalReviewCount(u_id, p_name);
	}

    public int getReviewCount(String u_id, String p_name) {
        return adminDao.selectReviewCount(u_id, p_name);
    }
}


