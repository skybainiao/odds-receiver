// src/main/java/com/example/autobet/repository/BindingRepository.java

package com.example.odds_receiver.Repository;


import com.example.odds_receiver.Model.Binding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BindingRepository extends JpaRepository<Binding, Long> {
    // 如果需要，可以在这里添加自定义查询方法
}
