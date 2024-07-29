package ra.project_md05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.project_md05.model.entity.OrderDetail;
import ra.project_md05.repository.OrderDetailRepository;
import ra.project_md05.service.OrderDetailService;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    public List<OrderDetail> getAllOrderDetail(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
