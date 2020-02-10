package com.solrgraphdemo.controllers

import com.solrgraphdemo.task.GraphSearchTask
import com.solrgraphdemo.task.CategoryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CategoriesController
@Autowired
constructor(private val graphSearchTask: GraphSearchTask) {

    @RequestMapping(value = ["/api/category/{id}"], method = [RequestMethod.GET])
    fun getGraph(@PathVariable("id") id: String): ResponseEntity<CategoryDto> {
        if (id.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        val categoryDto = graphSearchTask.runTask(id)
        return ResponseEntity.ok(categoryDto)
    }
}

