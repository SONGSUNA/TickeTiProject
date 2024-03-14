package com.office.ticketreserve.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.office.ticketreserve.productpage.CurrentReserveDto;
import com.office.ticketreserve.productpage.PerfomanceDto;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/category")
@Log4j2
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	
	@GetMapping("/concert")
	public String goConcert(Model model) {
		log.info("goConcertPage");
		
		String nextPage = "category/concert_page";
		
		List<PerfomanceDto> categoryDtos = categoryService.goCategory(CategoryConstants.CONCERT);
		model.addAttribute("categoryDtos", categoryDtos);
		
		String categoryName = categoryDtos.get(0).getP_category();
		List<CurrentReserveDto> rankingDtos = categoryService.getRanking(categoryName);
		model.addAttribute("rankingDtos",rankingDtos);
		
		List<PerfomanceDto> upcomingDtos = categoryService.getUpcoming(categoryName);		
		model.addAttribute("categoryUpcomingDtos",upcomingDtos);
		
		List<PerfomanceDto> newPerfomanceDtos = categoryService.getNewPerfomance(categoryName);		
		model.addAttribute("newPerfomanceDtos",newPerfomanceDtos);
		
		return nextPage;
	
	}
	@GetMapping("/musical")
	public String goMusical(Model model) {
		log.info("goMusicalPage");
		
		String nextPage = "/category/musical_page";
		
		List<PerfomanceDto> categoryDtos = categoryService.goCategory(CategoryConstants.MUSICAL);
		model.addAttribute("categoryDtos", categoryDtos);
		
		String categoryName = categoryDtos.get(0).getP_category();
		List<CurrentReserveDto> rankingDtos = categoryService.getRanking(categoryName);
		model.addAttribute("rankingDtos",rankingDtos);
		
		List<PerfomanceDto> upcomingDtos = categoryService.getUpcoming(categoryName);		
		model.addAttribute("categoryUpcomingDtos",upcomingDtos);
		
		List<PerfomanceDto> newPerfomanceDtos = categoryService.getNewPerfomance(categoryName);		
		model.addAttribute("newPerfomanceDtos",newPerfomanceDtos);
		
		return nextPage;
	
	}
	@GetMapping("/theater")
	public String goTheater(Model model) {
		log.info("goTheaterPage");
		
		String nextPage = "/category/theater_page";
		
		List<PerfomanceDto> categoryDtos = categoryService.goCategory(CategoryConstants.THEATER);
		model.addAttribute("categoryDtos", categoryDtos);
		
		String categoryName = categoryDtos.get(0).getP_category();
		List<CurrentReserveDto> rankingDtos = categoryService.getRanking(categoryName);
		model.addAttribute("rankingDtos",rankingDtos);
		
		List<PerfomanceDto> upcomingDtos = categoryService.getUpcoming(categoryName);		
		model.addAttribute("categoryUpcomingDtos",upcomingDtos);
		
		List<PerfomanceDto> newPerfomanceDtos = categoryService.getNewPerfomance(categoryName);		
		model.addAttribute("newPerfomanceDtos",newPerfomanceDtos);
		
		return nextPage;
	
	}
	@GetMapping("/classic")
	public String goClassic(Model model) {
		log.info("goClassicPage");
		
		String nextPage = "/category/classic_page";
		
		List<PerfomanceDto> categoryDtos = categoryService.goCategory(CategoryConstants.CLASSIC);
		model.addAttribute("categoryDtos", categoryDtos);
		
		String categoryName = categoryDtos.get(0).getP_category();
		List<CurrentReserveDto> rankingDtos = categoryService.getRanking(categoryName);
		model.addAttribute("rankingDtos",rankingDtos);
		
		List<PerfomanceDto> UpcomingDtos = categoryService.getUpcoming(categoryName);		
		model.addAttribute("categoryUpcomingDtos",UpcomingDtos);
		
		List<PerfomanceDto> newPerfomanceDtos = categoryService.getNewPerfomance(categoryName);		
		model.addAttribute("newPerfomanceDtos",newPerfomanceDtos);
		
		return nextPage;
	
	}
	@GetMapping("/koreanMusic")
	public String goExhibition(Model model) {
		log.info("gokoreanMusicPage");
		
		String nextPage = "/category/korean_music_page";
		
		List<PerfomanceDto> categoryDtos = categoryService.goCategory(CategoryConstants.KOREAN_MUSIC);
		model.addAttribute("categoryDtos", categoryDtos);
		
		String categoryName = categoryDtos.get(0).getP_category();
		List<CurrentReserveDto> rankingDtos = categoryService.getRanking(categoryName);
		model.addAttribute("rankingDtos",rankingDtos);
		
		List<PerfomanceDto> UpcomingDtos = categoryService.getUpcoming(categoryName);		
		model.addAttribute("categoryUpcomingDtos",UpcomingDtos);
		
		List<PerfomanceDto> newPerfomanceDtos = categoryService.getNewPerfomance(categoryName);		
		model.addAttribute("newPerfomanceDtos",newPerfomanceDtos);
		
		return nextPage;
	
	}
}
