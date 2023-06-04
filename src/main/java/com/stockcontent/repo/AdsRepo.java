package com.stockcontent.repo;

import com.stockcontent.model.Ads;
import com.stockcontent.model.enums.Category;
import com.stockcontent.model.enums.Type;
import com.stockcontent.model.enums.adStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdsRepo extends JpaRepository<Ads, Long> {
    List<Ads> findAllByStatus(adStatus status);

    List<Ads> findAllByNameContainingAndStatusAndDescription_CategoryAndDescription_Type(String name, adStatus status, Category category, Type type);

    List<Ads> findAllByStatusAndDescription_Category(adStatus status, Category category);

    List<Ads> findAllByStatusAndDescription_Type(adStatus status, Type type);
}
