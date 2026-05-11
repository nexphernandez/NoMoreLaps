package com.nomorelaps.infrastructure.odoo;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OdooClient {

    @Value("${odoo.url}")
    private String url;

    @Value("${odoo.db}")
    private String db;

    @Value("${odoo.username}")
    private String username;

    @Value("${odoo.password}")
    private String password;

    public Integer authenticate() throws Exception {
        XmlRpcClient client = new XmlRpcClient();
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));
        client.setConfig(config);

        return (Integer) client.execute("authenticate", Arrays.asList(db, username, password, Collections.emptyMap()));
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getActiveAds(Integer uid) throws Exception {
        XmlRpcClient client = new XmlRpcClient();
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
        client.setConfig(config);

        List<Object> args = Arrays.asList(
            db, uid, password,
            "nomorelaps.ad.campaign", "search_read",
            Collections.singletonList(
                Collections.singletonList(
                    Arrays.asList("state", "=", "active")
                )
            ),
            new java.util.HashMap<String, Object>() {{
                put("fields", Arrays.asList("name", "ad_type", "image_url", "target_url"));
            }}
        );

        Object[] result = (Object[]) client.execute("execute_kw", args);
        return Arrays.stream(result)
                     .map(obj -> (Map<String, Object>) obj)
                     .collect(Collectors.toList());
    }
}
