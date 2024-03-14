package com.office.ticketreserve;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.office.ticketreserve.kopisapi.KopisApi;
import com.office.ticketreserve.kopisapi.TicketDB;
import com.office.ticketreserve.productpage.CurrentReserveDto;
import com.office.ticketreserve.productpage.PerfomanceDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("")
public class HomeController  {
	@Autowired
	KopisApi api;
	@Autowired
	TicketDB tDB;
	@Autowired
	HomeService homeService;
	
	@GetMapping({"/", ""})
	public String home(Model model) throws JAXBException {
		log.info("[HomeController] home()");
		
		String nextPage = "/home";
		api.getMusicalInfo(3);		//인자값 1주면 전체 API땡겨옴 2주면 예매현황 땡겨옴 3주면 그냥 넘어감.
		//tDB.TicketStringFix();
		
		List<CurrentReserveDto> rankOnePerfos = homeService.getRankOnePerfo();
		model.addAttribute("rankOnePerfos", rankOnePerfos);
		
		List<PerfomanceDto> todayPerfos = homeService.getTodayPerfo();
		model.addAttribute("todayPerfos", todayPerfos);
		
		List<PerfomanceDto> nextPerfos = homeService.getnextPerfo();
		model.addAttribute("nextPerfos", nextPerfos);
		return nextPage;
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("search_value") String search,
	                    @RequestParam(defaultValue = "1", name = "pageNo") int pageNo,
	                    @RequestParam(defaultValue = "3", name = "pageSize") int pageSize,
	                    Model model) {
			log.info("[HomeController] search");
			
			if(pageNo <= 0) 
				pageNo =1;
			
			String nextPage = "/search_page";
			
			Page<PerfomanceDto> page = homeService.getSearchResult(search, pageNo, pageSize);
			model.addAttribute("searchValue", search);
			model.addAttribute("searchPerfos", page.getContent());
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			
			log.info("[HomeController] search" + page);
			log.info("[HomeController] search" + page.getNumber());
			log.info("[HomeController] search" + page.getTotalPages());
			
			
			
			return nextPage;
	}
	
}
