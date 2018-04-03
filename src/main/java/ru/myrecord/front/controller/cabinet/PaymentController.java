package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.adapters.UserAdapter;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserProduct;
import ru.myrecord.front.service.iface.ProductService;
import ru.myrecord.front.service.iface.RoomService;
import ru.myrecord.front.service.iface.UserProductService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PaymentController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserProductService userProductService;


    @RequestMapping(value="/cabinet/clients/payment", method = RequestMethod.GET)
    public ModelAndView index(Principal principal) {
        User user = userService.findUserByEmail( principal.getName() );
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("cabinet/client/payment/index");
        return modelAndView;
    }

}
