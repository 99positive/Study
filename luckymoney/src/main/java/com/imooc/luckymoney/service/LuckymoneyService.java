package com.imooc.luckymoney.service;

import com.imooc.luckymoney.dao.LuckymoneyRepository;
import com.imooc.luckymoney.entity.Luckymoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * Created by lqb
 * on 2019/4/25.
 */
@Service
public class LuckymoneyService {

    @Autowired
    private LuckymoneyRepository repository;

    @Transactional
    public void createTwo(){
        Luckymoney one = new Luckymoney();
        one.setConsumer("A");
        one.setMoney(new BigDecimal(520));
        repository.save(one);
        Luckymoney two = new Luckymoney();
        two.setConsumer("A");
        two.setMoney(new BigDecimal(1314));
        repository.save(two);

    }
}
