package com.imooc.luckymoney.controller;

import com.imooc.luckymoney.dao.LuckymoneyRepository;
import com.imooc.luckymoney.entity.Luckymoney;
import com.imooc.luckymoney.service.LuckymoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Created by lqb
 * on 2019/4/25.
 */
@RestController
public class LuckymoneyController {

    @Autowired
    private LuckymoneyRepository luckymoneyRepository;

    @Autowired
    private LuckymoneyService luckymoneyService;

    /**
     * 获取list
     * @return list
     */
    @GetMapping("/luckymoneys")
    public List<Luckymoney> list(){
        return luckymoneyRepository.findAll();
    }

    @PostMapping("/create")
    public Luckymoney create(@RequestParam("producer") String producer,
                             @RequestParam("money")BigDecimal money){
        Luckymoney luckymoney = new Luckymoney();
        luckymoney.setProducer(producer);
        luckymoney.setMoney(money);
        luckymoneyRepository.save(luckymoney);
        return luckymoney;
    }

    @GetMapping("/get")
    public Luckymoney findById(@RequestParam("id") Integer id) {
        return luckymoneyRepository.findById(id).orElse(null);
    }

    @PutMapping("/update/{id}")
    public Luckymoney update(@PathVariable("id") Integer id,
                             @RequestParam("consumer") String consumer) {
        Optional<Luckymoney> optional = luckymoneyRepository.findById(id);
        if (optional.isPresent()) {
            Luckymoney luckymoney = optional.get();
            luckymoney.setConsumer(consumer);
            return luckymoneyRepository.save(luckymoney);
        }
        return null;
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        try {
            luckymoneyRepository.deleteById(id);
            return "删除成功！";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "删除失败！";
    }

    @PostMapping("/send/two")
    public void sendTwo(){
        luckymoneyService.createTwo();
    }
}
