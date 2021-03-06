package bookmarks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * @author Josh Long
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BookmarkRestControllerTest {


    private MediaType contentType = new MediaType(
            "application", "hal+json");

    private MockMvc mockMvc;

    private String userName = "bdussault";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Account account;

    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.bookmarkRepository.deleteAllInBatch();
        this.accountRepository.deleteAllInBatch();

        this.account = accountRepository.save(new Account(userName, "password"));
        this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + userName, "A description")));
        this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + userName, "A description")));
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(post("/george/bookmarks/")
                .content(this.json(new Bookmark()))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readSingleBookmark() throws Exception {
        System.out.println("readSingleBookmark###############################################################################################");
        MvcResult result = mockMvc.perform(get("/" + userName + "/bookmarks/"
                + this.bookmarkList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.bookmark.id", is(this.bookmarkList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.bookmark.uri", is("http://bookmark.com/1/" + userName)))
                .andExpect(jsonPath("$.bookmark.description", is("A description")))
                .andExpect(jsonPath("$._links.self.href", containsString("/" + userName + "/bookmarks/"
                        + this.bookmarkList.get(0).getId()))).andReturn();
        if (result != null && !result.getResponse().getContentAsString().trim().isEmpty()) {
            System.out.println(result.getResponse().getContentAsString().trim());
        } else {
            System.out.println("No response json");
        }
        System.out.println("readSingleBookmark###############################################################################################");
    }

    @Test
    public void readBookmarks() throws Exception {
        System.out.println("readBookmarks###############################################################################################");
        MvcResult result = mockMvc.perform(get("/" + userName + "/bookmarks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$._embedded.bookmarkResourceList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.bookmarkResourceList[0].bookmark.id", is(this.bookmarkList.get(0).getId().intValue())))
                .andExpect(jsonPath("$._embedded.bookmarkResourceList[0].bookmark.uri", is("http://bookmark.com/1/" + userName)))
                .andExpect(jsonPath("$._embedded.bookmarkResourceList[0].bookmark.description", is("A description")))
                .andExpect(jsonPath("$._embedded.bookmarkResourceList[1].bookmark.id", is(this.bookmarkList.get(1).getId().intValue())))
                .andExpect(jsonPath("$._embedded.bookmarkResourceList[1].bookmark.uri", is("http://bookmark.com/2/" + userName)))
                .andExpect(jsonPath("$._embedded.bookmarkResourceList[1].bookmark.description", is("A description"))).andReturn();	
        if (result != null && !result.getResponse().getContentAsString().trim().isEmpty()) {
            System.out.println(result.getResponse().getContentAsString().trim());
        } else {
            System.out.println("No response json");
        }
        System.out.println("readBookmarks###############################################################################################");
    }

    @Test
    public void createBookmark() throws Exception {
        System.out.println("bookmarkJson###############################################################################################");
        String bookmarkJson = json(new Bookmark(
                this.account, "http://spring.io", "a bookmark to the best resource for Spring news and information"));
        System.out.println(bookmarkJson);
        System.out.println("bookmarkJson###############################################################################################");

        System.out.println("createBookmark###############################################################################################");
        MvcResult result = this.mockMvc.perform(post("/" + userName + "/bookmarks")
                .contentType(contentType)
                .content(bookmarkJson))
                .andExpect(status().isCreated()).andReturn();
        if (result != null && !result.getResponse().getContentAsString().trim().isEmpty()) {
            System.out.println(result.getResponse().getContentAsString().trim());
        } else {
            System.out.println("No response json");
        }
        System.out.println("createBookmark###############################################################################################");
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        System.out.println("mockHttpOutputMessage###############################################################################################");
        String mockHttpOutputMessageBody = mockHttpOutputMessage.getBodyAsString();
        System.out.println(mockHttpOutputMessageBody);
        System.out.println("mockHttpOutputMessage###############################################################################################");
        return mockHttpOutputMessageBody;
    }
}
