package com.mflix.gateway.proxy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyController {

    @RequestMapping("/**")
    public Object proxy() {
        return "Proxy option not completed yet";
    }
}
