package yukinami.example.saas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by snowblink on 14/11/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TenantInventoryControllerIntegrationTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testList() throws Exception {
        this.mockMvc.perform(get("/b1/tenant_inventories/").servletPath("/b1")).andExpect(status().isOk());
        this.mockMvc.perform(get("/tenant_inventories/")).andExpect(status().isNotFound());
    }

    @Test
    public void estRootList() throws Exception {
        this.mockMvc.perform(get("/tenant_inventories/root")).andExpect(status().isOk());
        this.mockMvc.perform(get("/b1/tenant_inventories/root").servletPath("/b1")).andExpect(status().isNotFound());
    }
}
