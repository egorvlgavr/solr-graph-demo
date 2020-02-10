package com.solrgraphdemo.task


import com.solrgraphdemo.model.Category
import com.solrgraphdemo.query.GraphQuery
import com.solrgraphdemo.utils.NoSuchCategoryException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

@Component
@Scope(value = SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
class GraphSearchTask @Autowired
constructor(private val graphQuery: GraphQuery) : SearchTask<String, CategoryDto> {

    override fun runTask(argument: String): CategoryDto {
        graphQuery.dept = argument
        val tags = graphQuery.execute()
        if (tags.isEmpty()) {
            throw NoSuchCategoryException("No category: $argument")
        }
        val dotFormat = buildDotFromCategories(tags)
        val graph = buildGraphFromCategories(tags)
        return CategoryDto(dotFormat, graph)
    }

    private fun buildDotFromCategories(categories: List<Category>): String {
        val ids = categories
                .map { it.id }
                .toHashSet();
        return categories
                .flatMap { category ->
                    val result = ArrayList<String>()
                    category.outEdges?.let {
                        edges -> edges
                                .filter { ids.contains(it) }
                                .forEach {
                                    result.add("\"" + category.id + "\"->\"" + it + "\"")
                                }
                    }
                    return@flatMap result
                }
                .joinToString("; ", "digraph {rankdir=LR; ", "}")
    }

    private fun buildGraphFromCategories(categories: List<Category>): List<Set<String>> {
        val jsonGraph = ArrayList<Set<String>>()
        val idToTag = categories.map { it.id to it }.toMap();
        val startKey = categories.first().id
        val positionToIdMap = HashMap<Int, MutableSet<String>>()
        dfs(idToTag, startKey, positionToIdMap, 0)
        positionToIdMap
                .keys
                .stream()
                .max { x, y -> Integer.compare(x, y) }
                .ifPresent { maxPositions ->
                    val fromTags = ArrayList<Set<String>>(maxPositions + 1)
                    positionToIdMap.entries
                            .forEach { entry -> fromTags.add(entry.key, entry.value) }
                    jsonGraph.addAll(fromTags)
                }
        return jsonGraph
    }

    private fun dfs(idToCategory: Map<String, Category>,
                    nextId: String,
                    positionToIdMap: MutableMap<Int, MutableSet<String>>,
                    position: Int) {
        val tag = idToCategory[nextId]
        if (tag != null) {
            val nodesOnPosition = positionToIdMap.computeIfAbsent(position) { HashSet() }
            nodesOnPosition.add(tag.id)
            val nextPosition = position + 1
            tag.outEdges?.let {
                for (out in it) {
                    dfs(idToCategory, out, positionToIdMap, nextPosition)
                }
            }
        }


    }
}

class CategoryDto(val dot: String, val graph: List<Set<String>>) {
}
