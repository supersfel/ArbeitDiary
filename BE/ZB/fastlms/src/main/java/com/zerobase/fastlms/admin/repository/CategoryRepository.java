package com.zerobase.fastlms.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerobase.fastlms.admin.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
}