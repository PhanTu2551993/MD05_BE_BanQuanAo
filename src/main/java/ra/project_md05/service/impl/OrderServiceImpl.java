package ra.project_md05.service.impl;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.project_md05.constants.OrderStatus;
import ra.project_md05.exception.DataNotFoundException;
import ra.project_md05.model.dto.response.OrderResponseRoleAdmin;
import ra.project_md05.model.entity.Orders;
import ra.project_md05.repository.OrderDetailRepository;
import ra.project_md05.repository.OrderRepository;
import ra.project_md05.repository.ProductRepository;
import ra.project_md05.service.IUserService;
import ra.project_md05.service.OrderService;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private IUserService userService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Orders> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }


    @Override
    public Page<OrderResponseRoleAdmin> getAllOrderRoleAdmin(Pageable pageable) {
        Page<Orders> orderPage = orderRepository.findAll(pageable);
        return getOrderResponseRoleAdmins(pageable, orderPage);
    }

    private Page<OrderResponseRoleAdmin> getOrderResponseRoleAdmins(Pageable pageable, Page<Orders> orderRepositoryByStatusOrderStatusName) {
        List<OrderResponseRoleAdmin> orderResponseRoleAdmins = new ArrayList<>();
        for (Orders order : orderRepositoryByStatusOrderStatusName) {
            OrderResponseRoleAdmin orderResponseRoleAdmin = OrderResponseRoleAdmin.builder()
                    .orderId(order.getId())
                    .status(order.getStatus())
                    .serialNumber(order.getSerialNumber())
                    .receiveAddress(order.getStreetAddress())
                    .totalPrice(order.getTotalPrice())
                    .createdAt(order.getCreatedAt())
                    .note(order.getNote())
                    .receivedAt(order.getReceiveAt())
                    .receiveName(order.getReceiveName())
                    .userName(order.getUser().getFullName())
                    .build();
            orderResponseRoleAdmins.add(orderResponseRoleAdmin);
        }
        return new PageImpl<>(orderResponseRoleAdmins, pageable, orderRepositoryByStatusOrderStatusName.getTotalElements());
    }


    @Override
    public OrderResponseRoleAdmin updateOrderStatusById(Long orderId, OrderStatus status) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
        return OrderResponseRoleAdmin.builder()
                .orderId(order.getId())
                .serialNumber(order.getSerialNumber())
                .userName(order.getUser().getUsername())
                .receiveName(order.getReceiveName())
                .receiveAddress(order.getStreetAddress())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .build();
    }

}
