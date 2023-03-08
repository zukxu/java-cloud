package com.zukxu.design.behavioral.command.demo2;

import com.zukxu.design.behavioral.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:20:45
 */
@RestController
public class TransferController {

    private AccountService accountService;

    private Invoker invoker;

    @Autowired
    public TransferController(AccountService accountService, Invoker invoker) {
        this.accountService = accountService;
        this.invoker = invoker;
    }

    @PostMapping("/transfer")
    public void transfer(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("amount") double amount) {
        Command command = new AccountTransferCommand(accountService, from, to, amount);
        invoker.setCommand(command);
        invoker.execute();
    }

    @PostMapping("/undo")
    public void undo() {
        invoker.undo();
    }

}
