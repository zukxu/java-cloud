package com.zukxu.design.behavioral.command.demo2;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 实现转账操作
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:23:22
 */
@Service
public class AccountService {

    private Map<String, Double> accounts = new HashMap<>();

    public AccountService() {
        accounts.put("Alice", 1000.0);
        accounts.put("Bob", 2000.0);
        accounts.put("Charlie", 3000.0);
    }

    /** 转账 */
    public void transfer(String from, String to, double amount) {
        double fromBalance = accounts.get(from);
        double toBalance = accounts.get(to);

        if(fromBalance < amount) {
            throw new RuntimeException("Insufficient funds in " + from + " account");
        }

        fromBalance -= amount;
        toBalance += amount;

        accounts.put(from, fromBalance);
        accounts.put(to, toBalance);
    }

    /** 获取账户余额 */
    public double getBalance(String account) {
        return accounts.get(account);
    }

}
