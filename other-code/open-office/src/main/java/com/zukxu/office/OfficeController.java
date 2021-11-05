package com.zukxu.office;

import com.zukxu.office.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xupu
 * @Date 2021-11-05 10:45
 */
@RestController
@RequestMapping
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @PostMapping("/api/file/onlinePreview")
    public void onlinePreview(@RequestParam("url") String url, HttpServletResponse response) throws Exception {
        officeService.onlinePreview(url, response);
    }

}
