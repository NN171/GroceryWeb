package edu.rut.grocery.controller.controllerImpl;

import edu.rut.web.controllers.model.OrderController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/orders")
public class OrderControllerImpl implements OrderController {
}
