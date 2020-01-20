package com.solrgraphdemo.query


import org.apache.solr.client.solrj.SolrClient

abstract class Query<T> internal constructor(internal val solrClient: SolrClient) {

    abstract fun execute(): T
}
