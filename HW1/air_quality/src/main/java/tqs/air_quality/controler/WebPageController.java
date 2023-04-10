package tqs.air_quality.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/index")
	public String index2() {
		return "index";
	}
}