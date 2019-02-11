package cn.echocow.xiaoming.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Echo
 * @version 1.0
 * @date 2019-02-08 21:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenUploadSuccess() throws Exception {
        String result = mockMvc.perform(multipart("/files/")
                .file(new MockMultipartFile("file", "阿萨德.txt", "multipart/form-data", "hello upload".getBytes(StandardCharsets.UTF_8)))
                .param("task", "1")
                .header("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsieGlhb01pbmciXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNTQ5OTQ5NTI0LCJhdXRob3JpdGllcyI6WyJST0xFX1NUVURFTlQiLCJST0xFX1NUVURZIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiI2ZTdiMTM2ZC0wYzU0LTRjOWUtOTAyYy03NjFhZThkMmQ4YzkiLCJjbGllbnRfaWQiOiJ4aWFvTWluZyJ9.wXHXZu-13cChmQx4Sh_3X3zQLRJtx0ZBgd9akaURh2M"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenDownloadSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/files/2"))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(mvcResult);
    }

    @Test
    public void whenQiniuDownloadSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/files/5"))
                .andExpect(status().isFound())
                .andExpect(header().exists("location"))
                .andReturn();
        System.out.println(mvcResult);
        System.out.println(mvcResult.getResponse().getHeader("location"));
    }



}