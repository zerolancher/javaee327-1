import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class solrJDemo {
    @Test
    public void  testAdd() throws Exception{
        String url = "http://192.168.200.128:8080/solr/collection1";
        SolrServer  server = new HttpSolrServer(url);

        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id","1");
        doc.setField("name","李小龙");
        server.add(doc,1000);
		system.out.println("哈哈哈");

    }

    @Test
    public void testFind() throws Exception{
        String url = "http://192.168.200.128:8080/solr/collection1";
        SolrServer  server = new HttpSolrServer(url);
        SolrQuery solrQuery = new SolrQuery();

        solrQuery.set("q","台灯");  //照着面板抄
        solrQuery.set("fq","product_price:[0 TO 10.5}");

        solrQuery.setSort("product_price", SolrQuery.ORDER.desc);

        solrQuery.setStart(0);
        solrQuery.setRows(10);

        solrQuery.set("fl","id,product_name");
        solrQuery.set("df","product_keywords");

        solrQuery.setHighlight(true);

        //需要高亮的域名
        solrQuery.addHighlightField("product_name");

        solrQuery.setHighlightSimplePre("<em style = 'color' ':red'>");
        solrQuery.setHighlightSimplePost("</em>");





        QueryResponse response = server.query(solrQuery);

        Map<String, Map<String, List<String>>> map = response.getHighlighting();


        SolrDocumentList docs = response.getResults();
        long numFound = docs.getNumFound();
        System.out.println("总条数"+numFound);

        for (SolrDocument doc : docs) {
            System.out.println("id:"+doc.get("id"));

            Map<String, List<String>> listMap = map.get(doc.get("id"));
            List<String> list = listMap.get("product_name");
            if(list != null && list.size() >0){
                System.out.println("高亮的名称"+list.get(0));
            }
            else{
                System.out.println("product_name:"+doc.get("product_name"));
            }


            System.out.println("product_price:"+doc.get("product_price"));

        }


    }

    @Test
    public void testDelete() throws Exception{
        String url = "http://192.168.200.128:8080/solr/collection1";
        SolrServer  server = new HttpSolrServer(url);


    server.deleteById("2230",1000);
    server.deleteByQuery("*:*",1000);

    }
}
