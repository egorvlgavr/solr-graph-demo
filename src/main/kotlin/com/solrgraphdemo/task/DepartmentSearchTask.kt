package com.solrgraphdemo.task

import com.solrgraphdemo.query.GraphQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext
import java.lang.IllegalStateException

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
class DepartmentSearchTask
@Autowired
constructor(private val graphQuery: GraphQuery) : SearchTask<String, List<String>> {

    override fun runTask(argument: String): List<String> {
        graphQuery.maxDepth = 0
        graphQuery.dept = argument
        val rootCategories = graphQuery.execute()
        if (rootCategories.size != 1) {
            throw IllegalStateException("There should be only one root category but found ${rootCategories.size}")
        }
        return rootCategories[0].outEdges ?: throw IllegalStateException("Root category should have a departments as out edges")
    }

}