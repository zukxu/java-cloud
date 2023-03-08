package com.zukxu.design.behavioral.command.demo2;

import com.zukxu.design.behavioral.command.Command;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:18:21
 */
@Service
public class AccountTransferCommand implements Command {

    private AccountService accountService;

    /** 转出账户名 */
    private String fromAccount;

    /** 转入账户名 */
    private String toAccount;

    /** 转账金额 */
    private double amount;

    public AccountTransferCommand(AccountService accountService, String fromAccount, String toAccount, double amount) {
        this.accountService = accountService;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public void execute() {
        accountService.transfer(fromAccount, toAccount, amount);
    }

    public void undo() {
        accountService.transfer(toAccount, fromAccount, amount);
    }

}
