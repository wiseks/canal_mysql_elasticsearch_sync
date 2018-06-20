package com.star.sync.elasticsearch.client;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author <a href="mailto:wangchao.star@gmail.com">wangchao</a>
 * @version 1.0
 * @since 2017-08-25 17:32:00
 */
@Component
public class ElasticsearchClient implements DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchClient.class);
    private TransportClient transportClient;

    @Value("${elasticsearch.cluster.name}")
    private String clusterName;
    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private String port;

    @Bean
    public TransportClient getTransportClient() throws Exception {
        Settings settings = Settings.builder().put("cluster.name", clusterName)
                .put("client.transport.sniff", true)
                .build();
        transportClient = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(host), Integer.valueOf(port)));
        logger.info("elasticsearch transportClient 连接成功");
        
//        IndexResponse response = transportClient
//				.prepareIndex("bank2", "account2").setSource(this.getMapping()).execute().actionGet();
//		System.out.println(response.getId() + "====" + response.getIndex() + "====" + response.getType());
        return transportClient;
    }
    
    private static XContentBuilder getMapping(){
		XContentBuilder mapping = null;
		try{
			mapping = XContentFactory.jsonBuilder().startObject().startObject("account2").startObject("properties");
			mapping.startObject("account_number").field("type", "integer").field("store","yes").endObject()
				.startObject("balance").field("type","integer").field("store","yes").endObject()
				.startObject("firstname").field("type","string").field("store","yes").endObject()
				.startObject("lastname").field("type","string").field("store","yes").endObject()
				.startObject("age").field("type","integer").field("store","yes").endObject()
				.startObject("gender").field("type","string").field("store","yes").endObject()
				.startObject("address").field("type","string").field("store","yes").endObject()
				.startObject("employer").field("type","string").field("store","yes").endObject()
				.startObject("email").field("type","string").field("store","yes").endObject()
				.startObject("city").field("type","string").field("store","yes").endObject()
				.startObject("state").field("type","string").field("store","yes").endObject()
				.startObject("createtime").field("type","date").field("store","yes").endObject();
			mapping.endObject().endObject().endObject();
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping;
	}

    @Override
    public void destroy() throws Exception {
        if (transportClient != null) {
            transportClient.close();
        }
    }
}
