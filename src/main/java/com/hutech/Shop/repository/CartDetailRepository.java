package com.hutech.Shop.repository;
import com.hutech.Shop.model.CartDetail;
import com.hutech.Shop.model.CartDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, CartDetailId> {
}
