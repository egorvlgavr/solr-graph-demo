package com.solrgraphdemo.query


import com.solrgraphdemo.config.SolrProperties
import com.solrgraphdemo.model.Category
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.SolrServerException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.io.IOException
import java.lang.RuntimeException
import java.util.*


@Component
@Scope(SCOPE_PROTOTYPE)
class GraphQuery
@Autowired
constructor(solrClient: SolrClient, solrProperties: SolrProperties) : Query<List<Category>>(solrClient) {
    var dept: String? = null
    private val collection: String = solrProperties.tagCollection
    var maxDepth: Int = -1

    override fun execute(): List<Category> {
        if (Objects.isNull(dept)) {
            throw IllegalStateException("Departament can't be null!")
        }
        val solrQuery = SolrQuery(
                "{!graph " +
                        "from=id " +
                        "to=out_edge_ss " +
                        "traversalFilter='is_active_i:1' " +
                        "maxDepth=$maxDepth " +
                        "v=\$deptQuery}")
        solrQuery.add("deptQuery", "{!term f=id v=\$dept}")
        solrQuery.add("dept", dept)
        solrQuery.setFields("id", "out_edge_ss")
        solrQuery.rows = 100
        return try {
            solrClient.query(collection, solrQuery)
                    .results
                    .map { Category(it["id"] as String, it["out_edge_ss"] as? List<String>) }
        } catch (e: SolrServerException) {
            throw RuntimeException("Error from Solr")
        } catch (e: IOException) {
            throw RuntimeException("Error from Solr")
        }

    }

}
