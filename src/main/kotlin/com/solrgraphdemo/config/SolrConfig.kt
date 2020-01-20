package com.solrgraphdemo.config

import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(SolrProperties::class)
class SolrConfig {

    @Bean
    fun solrClient(solrProperties: SolrProperties): SolrClient {
        return HttpSolrClient.Builder()
                .withBaseSolrUrl("http://" + solrProperties.host + "/solr/")
                .build()
    }
}
