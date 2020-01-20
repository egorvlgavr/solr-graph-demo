package com.solrgraphdemo.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UiController {

    @GetMapping(value = ["/"])
    fun indexPage(): String {
        return "categories.html"
    }
}
