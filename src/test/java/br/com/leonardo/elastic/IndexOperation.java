package br.com.leonardo.elastic;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

public class IndexOperation {

	public static void createIndex(Client client, String indexName) {
		CreateIndexRequestBuilder createIndexRequestBuilder = client.admin()
				.indices().prepareCreate(indexName);
		createIndexRequestBuilder.execute().actionGet();
	}

	
	
	public static void createIndexAndDocument(Client client, String indexName,
			String indexType) {
		try {
			client.prepareIndex(indexName, indexType)
					.setSource(
							jsonBuilder().startObject().field("user", "kimchy")
									.field("postDate", new Date())
									.field("message", "trying out ES")
									.endObject()).execute().actionGet();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String addDocumentToIndex(Client client, String indexName,
			String indexType, Map<String, Object> document) {
		IndexResponse response = client.prepareIndex(indexName, indexType)
				.setSource(document).execute().actionGet();
		return response.getId();
	}
}
