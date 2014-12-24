package com.baosight.scc.ec.search.client;

import com.baosight.scc.ec.search.properties.ElasticClientProperties;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;

import java.util.ArrayList;
import java.util.List;

public class ElasticClientFactory {
    private static Client transportClient;
    private static Client nodeClient;
    private ElasticClientProperties elasticClientProperties;


    public ElasticClientProperties getElasticClientProperties() {
        return elasticClientProperties;
    }


    public void setElasticClientProperties(
            ElasticClientProperties elasticClientProperties) {
        this.elasticClientProperties = elasticClientProperties;
    }


    public Client getOrCreateTransportClient() {
        if (transportClient == null) {
            synchronized (ElasticClientProperties.class) {
                if (transportClient == null) {
                    Settings settings = ImmutableSettings.settingsBuilder()
                            .put("cluster.name", elasticClientProperties.getClusterName()).put("client.transport.sniff", true).build();
                    List<TransportAddress> address = new ArrayList<TransportAddress>();
                    for (String server : elasticClientProperties.getServerAddress()) {
                        address.add(new InetSocketTransportAddress(server, 9300));
                    }
                    transportClient = new TransportClient(settings).addTransportAddresses(address.toArray(new TransportAddress[0]));
                    ;
                }

            }
        }

        return transportClient;

    }


}
