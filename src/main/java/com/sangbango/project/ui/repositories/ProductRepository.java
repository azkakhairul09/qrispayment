package com.sangbango.project.ui.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sangbango.project.ui.entitymodel.ProductEntity;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long> {
	ProductEntity findByProductId(String productId);
	Page<ProductEntity> findByIsActiveTrueOrderByIdDesc(Pageable pageableRequest);
	List<ProductEntity> findByIsActiveTrueOrderByCreatedDateDesc();
	ProductEntity findByProductIdAndIsActiveTrue(String productId);
}
