package com.duyhk.clothing_ecommerce.service;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.dto.search.SearchBillDTO;
import com.duyhk.clothing_ecommerce.entity.Bill;

import java.util.List;

public interface BillService {
    Bill convertToEntity(BillDTO billDTO);

    BillDTO convertToDto(Bill bill);

    List<BillDTO> getAll();

    PageDTO<List<BillDTO>> getByPageRequest(PageRequestDTO pageRequestDTO);

    PageDTO<List<BillDTO>> search(SearchBillDTO searchBillDTO);
    PageDTO<List<BillDTO>> getSellAtStore(PageRequestDTO pageRequestDTO);
    PageDTO<List<BillDTO>> getByStatus(PageRequestDTO pageRequestDTO, Integer status);

    BillDTO getById(Long id);

    Bill createAtStore(BillDTO billDTO);

    void updateSellAtStoreFinal(BillDTO billDTO);

    void updateStatusById(Long id, Integer status);

    void delete(Long id);

    void cancelBill(Long id);

}
