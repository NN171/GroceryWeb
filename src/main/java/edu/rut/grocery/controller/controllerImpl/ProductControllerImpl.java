package edu.rut.grocery.controller.controllerImpl;

import edu.rut.web.controllers.model.ProductController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/products")
public class ProductControllerImpl implements ProductController {
}
