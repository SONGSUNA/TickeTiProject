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
		
		List<PerfomanceDto> categoryDtos = categoryService.goConcert();
		model.addAttribute("categoryDtos", categoryDtos);
		String categoryNameForRanking = categoryDtos.get(0).getP_category();
		List<CurrentReserveDto> rankingDtos = categoryService.getRanking(categoryNameForRanking);
		model.addAttribute("rankingDtos",rankingDtos);
		
		return nextPage;
	
	}
	@GetMapping("/musical")
	public String goMusical() {
		log.info("goMusicalPage");
		
		String nextPage = "/category/musical_page";
		
		return nextPage;
	
	}
	@GetMapping("/theater")
	public String goTheater() {
		log.info("goTheaterPage");
		
		String nextPage = "/category/theater_page";
		
		return nextPage;
	
	}
	@GetMapping("/classic")
	public String goClassic() {
		log.info("goClassicPage");
		
		String nextPage = "/category/classic_page";
		
		return nextPage;
	
	}
	@GetMapping("/koreanMusic")
	public String goExhibition() {
		log.info("gokoreanMusicPage");
		
		String nextPage = "/category/korean_music_page";
		
		return nextPage;
	
	}
}
