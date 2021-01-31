package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class {@code OrderController} uses to work with order information.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    /**
     * Instantiates a new Order controller.
     *
     * @param orderService the order service
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Get order by id order dto.
     *
     * @param id the id
     * @return the order dto
     */
    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable long id) {
        OrderDto orderDto = orderService.findById(id);
        addRelationship(orderDto);
        return orderDto;
    }

    /**
     * Add order response entity.
     *
     * @param orderDto the order dto
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<String> addOrder(@RequestBody OrderDto orderDto) {
        long orderId = orderService.add(orderDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderId)
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        addRelationship(orderDto);
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    /**
     * Delete order.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable long id) {
        orderService.remove(id);
    }

    /**
     * Gets list of orders by user id.
     *
     * @param userId     the user id
     * @param pageNumber the page number
     * @param size       the size
     * @return the orders by user id
     */
    @GetMapping("/users/{userId}")
    public List<OrderDto> getOrdersByUserId(@PathVariable long userId,
                                            @RequestParam(required = false, defaultValue = "1") int pageNumber,
                                            @RequestParam(required = false, defaultValue = "5") int size) {
        PageDto pageDto = new PageDto(size, pageNumber);
        List<OrderDto> orders = orderService.findByUserId(userId, pageDto);
        orders.forEach(this::addRelationship);
        return orders;
    }

    private void addRelationship(OrderDto orderDto) {
        orderDto.add(linkTo(methodOn(OrderController.class).getOrderById(orderDto.getId())).withSelfRel());
    }
}
