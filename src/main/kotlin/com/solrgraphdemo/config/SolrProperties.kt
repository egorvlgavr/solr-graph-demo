package com.solrgraphdemo.config

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties("solr")
class SolrProperties {
    lateinit var host: String
    lateinit var tagCollection: String
}
