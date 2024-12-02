package com.root.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.root.admin.entity.Product;

@Repository
public interface AdminRepository  extends JpaRepository<Product,Long>{

}
