package edu.rut.grocery.controller;

import edu.rut.grocery.dto.HighRatedDto;
import edu.rut.grocery.dto.ProductDto;
import edu.rut.grocery.service.AuthService;
import edu.rut.grocery.service.CustomService;
import edu.rut.web.controllers.CustomController;
import edu.rut.web.dto.base.BaseViewModel;
import edu.rut.web.dto.custom.FrequentViewModel;
import edu.rut.web.dto.custom.HighRatedViewModel;
import edu.rut.web.dto.custom.ProductFrequentViewModel;
import edu.rut.web.dto.custom.ProductRateViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;

@Controller
@RequestMapping("/customs")
public class CustomControllerImpl implements CustomController {

	private final CustomService customService;
	private final AuthService authService;
	private static final Logger LOG = LoggerFactory.getLogger(CustomControllerImpl.class);

	public CustomControllerImpl(CustomService customService, AuthService authService) {
		this.customService = customService;
		this.authService = authService;
	}

	@GetMapping("/highrates")
	public String getHighRated(Model model) {

		LOG.info("Вызов метода высокооцененных продуктов");
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
	public String getFrequents(Model model,
							   @AuthenticationPrincipal UserDetails userDetails) {

		LOG.info("Вызов метода популярных продуктов пользователя");
		Long id = authService.getUser(userDetails.getUsername()).getCustomer().getId();
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
		LOG.info("Успешное выполнение getFrequents");

		return "custom/popular";
	}

	public BaseViewModel createBaseViewModel(String title) {
		return new BaseViewModel(title);
	}
}
