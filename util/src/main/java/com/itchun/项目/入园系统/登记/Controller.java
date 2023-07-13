package com.itchun.项目.入园系统.登记;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ry")
public class Controller {

    /**
     * 入园生成日报
     */
    @RequestMapping("/word.do")
    public String word(Map<String, Object> params) {
        String out_path = (String) params.get("out_path");
        params.remove("out_path");
        WordReport.ry(params, out_path);
        return "ok";
    }

}
