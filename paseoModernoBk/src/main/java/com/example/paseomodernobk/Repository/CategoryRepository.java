package com.example.paseomodernobk.Repository;


import com.example.paseomodernobk.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Locale;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
