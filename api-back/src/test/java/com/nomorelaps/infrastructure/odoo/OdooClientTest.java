package com.nomorelaps.infrastructure.odoo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit tests for OdooClient.
 * Adheres to the New Backend Test Refactoring Plan for granularity and business naming.
 * Uses mockConstruction to intercept internal object creation without modifying source code.
 */
@ExtendWith(MockitoExtension.class)
class OdooClientTest {

    @InjectMocks
    private OdooClient odooClient;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(odooClient, "url", "http://odoo.test");
        ReflectionTestUtils.setField(odooClient, "db", "test_db");
        ReflectionTestUtils.setField(odooClient, "username", "admin");
        ReflectionTestUtils.setField(odooClient, "password", "secret");
    }

    @Test
    @DisplayName("authenticate - Success: Should return user ID")
    void authenticate_Success_ShouldReturnUid() throws Exception {
        try (MockedConstruction<XmlRpcClient> mockedClient = mockConstruction(XmlRpcClient.class,
                (mock, context) -> {
                    when(mock.execute(eq("authenticate"), anyList())).thenReturn(42);
                });
             MockedConstruction<XmlRpcClientConfigImpl> mockedConfig = mockConstruction(XmlRpcClientConfigImpl.class)) {
            
            Integer uid = odooClient.authenticate();

            assertEquals(42, uid);
            assertEquals(1, mockedClient.constructed().size());
        }
    }

    @Test
    @DisplayName("getActiveAds - Success: Should return list of ad maps")
    void getActiveAds_Success_ShouldReturnAdList() throws Exception {
        Map<String, Object> ad = new HashMap<>();
        ad.put("name", "Promo");
        Object[] result = new Object[]{ad};

        try (MockedConstruction<XmlRpcClient> mockedClient = mockConstruction(XmlRpcClient.class,
                (mock, context) -> {
                    when(mock.execute(eq("execute_kw"), anyList())).thenReturn(result);
                });
             MockedConstruction<XmlRpcClientConfigImpl> mockedConfig = mockConstruction(XmlRpcClientConfigImpl.class)) {

            List<Map<String, Object>> ads = odooClient.getActiveAds(42);

            assertNotNull(ads);
            assertEquals(1, ads.size());
            assertEquals("Promo", ads.get(0).get("name"));
        }
    }

    @Test
    @DisplayName("getActiveAds - Empty Result: Should return empty list")
    void getActiveAds_EmptyResult_ShouldReturnEmptyList() throws Exception {
        try (MockedConstruction<XmlRpcClient> mockedClient = mockConstruction(XmlRpcClient.class,
                (mock, context) -> {
                    when(mock.execute(eq("execute_kw"), anyList())).thenReturn(new Object[0]);
                });
             MockedConstruction<XmlRpcClientConfigImpl> mockedConfig = mockConstruction(XmlRpcClientConfigImpl.class)) {

            List<Map<String, Object>> ads = odooClient.getActiveAds(42);

            assertTrue(ads.isEmpty());
        }
    }
}
