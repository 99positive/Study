package com.imooc.luckymoney.dao;

import com.imooc.luckymoney.entity.Luckymoney;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lqb
 * on 2019/4/25.
 */
public interface LuckymoneyRepository extends JpaRepository<Luckymoney, Integer> {
}
