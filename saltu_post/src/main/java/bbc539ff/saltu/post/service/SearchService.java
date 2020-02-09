package bbc539ff.saltu.post.service;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

import static org.elasticsearch.action.support.WriteRequest.RefreshPolicy.IMMEDIATE;

//@Service
public class SearchService {
  @Autowired RestHighLevelClient highLevelClient;
  RestClient lowLevelClient = highLevelClient.getLowLevelClient();

  public void test() throws IOException {
    IndexRequest request =
        new IndexRequest("spring-data", "elasticsearch", "1")
            .source(Collections.singletonMap("feature", "high-level-rest-client"))
            .setRefreshPolicy(IMMEDIATE);

    IndexResponse response = highLevelClient.index(request);
  }
}
