package com.taotao.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDStation on 2016/8/4 0004.
 */
public class HttpClientTest {
    @Test
    public void doGet() throws IOException {
        //创建一个HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个Get对象
        HttpGet get = new HttpGet("http://wap.lexun.com");
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取响应的结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entity = response.getEntity();
        String s = EntityUtils.toString(entity);
        System.out.println(s);
        //关闭HttpClient
        response.close();
        httpClient.close();
    }
    @Test
    public void doGetWithParam() throws URISyntaxException, IOException {
        //创建一个HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置URIBuilder
        URIBuilder uriBuilder = new URIBuilder("http://www.sogou.com/web");
        uriBuilder.addParameter("query","诛仙青云志");
        //创建一个HttpGet对象
        HttpGet get = new HttpGet(uriBuilder.build());
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取响应的结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entity = response.getEntity();
        String s = EntityUtils.toString(entity);
        System.out.println(s);
        //关闭HttpClient
        response.close();
        httpClient.close();
    }
    @Test
    public void httpClientTestPost() throws IOException {
        //创建一个HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个HttpPost对象
        HttpPost post = new HttpPost("http://zhidao.baidu.com/submit/ajax/");
        //执行请求
        CloseableHttpResponse response = httpClient.execute(post);
        //拿结果
        int code = response.getStatusLine().getStatusCode();
        System.out.println("请求状态码："+code);
        HttpEntity entity = response.getEntity();
        String s = EntityUtils.toString(entity);
        System.out.println(s);
        //关闭HttpClient
        response.close();
        httpClient.close();
    }
    @Test
    public void httpClientTestPostWithParam() throws URISyntaxException, IOException {
        //创建一个HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个NameValuePar模拟表单
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("cm","100669"));
        list.add(new BasicNameValuePair("qid","872825299508273232"));
        list.add(new BasicNameValuePair("aid","18439605651"));
        list.add(new BasicNameValuePair("type","2"));
        list.add(new BasicNameValuePair("stoken","0856f38ea7eea950ac04a5e026cce042"));
        //创建一个StringEntity存放表单
        StringEntity entity = new UrlEncodedFormEntity(list);
        //创建一个HttpPost实例，并把表单数据添加到HttpPost请求中
        HttpPost post = new HttpPost("http://zhidao.baidu.com/submit/ajax/");
        post.setEntity(entity);
        //执行请求
        CloseableHttpResponse response = httpClient.execute(post);
        //拿结果
        int code = response.getStatusLine().getStatusCode();
        System.out.println("请求状态码："+code);
        HttpEntity entity1 = response.getEntity();
        String s = EntityUtils.toString(entity1);
        System.out.println(s);
        //关闭HttpClient
        response.close();
        httpClient.close();
    }
}
