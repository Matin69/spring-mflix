package com.mflix.gateway;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/**/**")
public class ProxyController {

    public Object proxy() {
        return "Proxy option not completed yet";
    }
}
