package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value="/deleteOrder/{orderId:[0-9]+}")
    public ModelAndView deleteOrder(@PathVariable("orderId") final long orderId){
        orderService.deleteOrder(orderId);
        return new ModelAndView("redirect:/sellerProfile");
    }
}
