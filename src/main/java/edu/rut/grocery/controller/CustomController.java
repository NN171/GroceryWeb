package edu.rut.grocery.controller;

import edu.rut.grocery.dto.HighRatedDto;
import edu.rut.grocery.dto.ProductDto;
import edu.rut.grocery.service.CustomService;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.custom.FrequentViewModel;
import edu.rut.web.dto.custom.HighRatedViewModel;
import edu.rut.web.dto.custom.ProductFrequentViewModel;
import edu.rut.web.dto.custom.ProductRateViewModel;
import edu.rut.web.dto.product.ProductViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/customs")
public class CustomController {

	private final CustomService customService;

	public CustomController(CustomService customService) {
		this.customService = customService;
	}

	@GetMapping("/highrates")
	public String getHighRated(Model model) {

		List<HighRatedDto> highRatedDto = customService.getHighRatedProducts();

		List<ProductRateViewModel> productViewModels = highRatedDto.stream()
				.map(p -> new ProductRateViewModel(
						p.getName(),
						p.getPrice(),
						p.getAvgRating()
				)).toList();

		HighRatedViewModel highRatedViewModel = new HighRatedViewModel(
				createBaseViewModel("High rated"),
				productViewModels
				);

		model.addAttribute("model", highRatedViewModel);

		return "custom/high-rated";
	}

	@GetMapping("/frequents")
	public String getFrequents(@RequestParam Long id,
							   Model model) {

		List<ProductDto> productDto = customService.alwaysOrdering(id);

		List<ProductFrequentViewModel> productViewModels = productDto.stream()
				.map(p -> new ProductFrequentViewModel(
						p.getName(),
						p.getPrice(),
						p.getProdDate(),
						p.getExpiryDate(),
						p.getAvgRating()
				)).toList();

		FrequentViewModel viewModel = new FrequentViewModel(
				createBaseViewModel("Frequent bought"),
				productViewModels
		);

		model.addAttribute("model", viewModel);

		return "custom/popular";
	}

	public BaseViewModel createBaseViewModel(String title) {
		return new BaseViewModel(title);
	}
}
