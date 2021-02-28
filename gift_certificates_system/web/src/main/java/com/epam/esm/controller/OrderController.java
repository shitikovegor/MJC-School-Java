package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.PageCollection;
import com.epam.esm.util.PageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PostAuthorize("hasRole('admin') or returnObject.user.id == principal.userId")
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
    @PreAuthorize("hasRole('admin') or #userId == principal.userId")
    @PostMapping("/users/{userId}")
    public ResponseEntity<String> addOrder(@PathVariable long userId, @RequestBody OrderDto orderDto) {
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        orderDto.setUser(userDto);
        long orderId = orderService.add(orderDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("orders/{id}")
                .buildAndExpand(orderId)
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        addRelationship(orderDto);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
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
     * @param userId the user id
     * @param page   the page number
     * @param size   the size
     * @return the orders by user id
     */
    @PreAuthorize("hasRole('admin') or #userId == principal.userId")
    @GetMapping("/users/{userId}")
    public ResponseEntity<PageCollection<OrderDto>> getOrdersByUserId(@PathVariable long userId,
                                                                      @RequestParam(required = false, defaultValue = "1") int page,
                                                                      @RequestParam(required = false, defaultValue = "5") int size) {
        PageDto pageDto = new PageDto(size, page);
        pageDto.setTotalRecords(orderService.findTotalRecordsByUserId(userId));
        List<OrderDto> orders = orderService.findByUserId(userId, pageDto);
        PageCollection<OrderDto> collection = new PageCollection<>(orders, pageDto.getTotalRecords());
        orders.forEach(this::addRelationship);
        addPageRelationship(collection, pageDto, userId);
        return ResponseEntity.ok(collection);
    }

    private void addRelationship(OrderDto orderDto) {
        orderDto.add(linkTo(methodOn(OrderController.class).getOrderById(orderDto.getId())).withSelfRel());
    }

    private void addPageRelationship(PageCollection<OrderDto> orderCollection, PageDto pageDto, long userId) {
        PageFormatter formatter = new PageFormatter();
        int lastPage = formatter.calculateLastPage(pageDto);
        if (pageDto.getPageNumber() < lastPage) {
            orderCollection.add(linkTo(methodOn(OrderController.class)
                    .getOrdersByUserId(userId, formatter.calculateNextPage(pageDto), pageDto.getSize()))
                    .withRel("next_page"));
        }
        if (pageDto.getPageNumber() > 1) {
            orderCollection.add(linkTo(methodOn(OrderController.class)
                    .getOrdersByUserId(userId, formatter.calculatePrevPage(pageDto), pageDto.getSize()))
                    .withRel("previous_page"));
        }
        orderCollection.add(linkTo(methodOn(OrderController.class)
                .getOrdersByUserId(userId, PageDto.FIRST_PAGE, pageDto.getSize()))
                .withRel("first_page"));
        orderCollection.add(linkTo(methodOn(OrderController.class)
                .getOrdersByUserId(userId, lastPage, pageDto.getSize()))
                .withRel("last_page"));
    }
}
