package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.entities.*;
import ru.myrecord.front.service.iface.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class RecordController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientRecordService clientRecordService;

    @Autowired
    private ClientRecordProductService clientRecordProductService;

    /**
     * Форма записи клиента
     * */
    @RequestMapping(value="/cabinet/clients/record/", method = RequestMethod.GET)
    public ModelAndView showClientRecords(Principal principal) {
        return new ModelAndView("/cabinet/client/record/index");
    }


    /**
     * Форма записи клиента
     * */
    @RequestMapping(value="/cabinet/clients/record/add/", method = RequestMethod.GET)
    public ModelAndView addClientRecord(Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        Set<Product> products = productService.findProductsByOwnerUser(ownerUser);

        Set<User> masters = userService.findUsersByOwner(ownerUser);

        User client = new User();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.addObject("masters", masters);
        modelAndView.addObject("client", client);
        modelAndView.setViewName("cabinet/client/record/edit");
        return modelAndView;
    }


    /**
     * Сохранение записи клиента
     * */
    //ToDo: do
    @RequestMapping(value="/cabinet/clients/record/add/", method = RequestMethod.POST)
    public ModelAndView editClientRecordPost(User client,
                                             Integer productId,
                                             Integer masterId,
                                             String sdate,
                                             Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( true ) {
            //ToDo: принадлежит сист.пользователю продуктЫ, клиент

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime recordDate = LocalDateTime.parse(sdate, formatter);

            User ownerUser = userService.findUserByEmail(principal.getName());
            ClientRecord clientRecord = new ClientRecord(client);
            clientRecord = clientRecordService.add(clientRecord, ownerUser);

            ClientRecordProduct clientRecordProduct = new ClientRecordProduct();
            clientRecordProduct.setRecord(clientRecord);
            Product product = productService.findProductById(productId);
            clientRecordProduct.setProduct(product);
            User master = userService.findUserById(masterId);
            clientRecordProduct.setMaster(master);
            clientRecordProduct.setSdate(recordDate);
            clientRecordProductService.add(clientRecordProduct);

            return new ModelAndView("redirect:/cabinet/clients/record/");
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


}
