package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VoucherReponsitory extends JpaRepository<Voucher,Long> {
    @Query("select v from Voucher v where v.status = 1 and v.dateStart <= NOW() and v.dateEnd > now() and v.quantity > 0")
    Page<Voucher> getKh(Pageable pageable);

    Optional<Voucher> findByVoucherCode(String voucherCode);
}
