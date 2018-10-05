package ekindergarten.test.controller;

import ekindergarten.domain.Address;
import ekindergarten.service.AddressService;
import ekindergarten.testingUtils.BaseTestContext;
import ekindergarten.testingUtils.Constans;
import ekindergarten.testingUtils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
public class AddressControllerTest extends BaseTestContext {

    private static final String URL_TEMPLATE = "/address/";

    @MockBean
    private AddressService addressService;

    private Address address;

    @Before
    public void setup() {
        super.setup();

        address = TestUtil.createAddress();
    }

    @Test
    public void shouldPassValidationAndAddAddress() throws Exception {

        when(addressService.addAddress(address, "user")).thenReturn(address);

        mockMvc.perform(
                post(URL_TEMPLATE + "add").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(address)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city", is(Constans.CITY)))
                .andExpect(jsonPath("$.zipCode", is(Constans.ZIP_CODE)))
                .andExpect(jsonPath("$.street", is(Constans.STREET)))
                .andExpect(jsonPath("$.homeNumber", is(Constans.HOME_NUMBER)))
                .andExpect(jsonPath("$.flatNumber", is(Constans.FLAT_NUMBER)));
    }

    @Test
    public void shouldPassValidationAndUpdateAddress() throws Exception {

        when(addressService.updateAddress(address, "user")).thenReturn(address);

        mockMvc.perform(
                put(URL_TEMPLATE + "update").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(address)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city", is(Constans.CITY)))
                .andExpect(jsonPath("$.zipCode", is(Constans.ZIP_CODE)))
                .andExpect(jsonPath("$.street", is(Constans.STREET)))
                .andExpect(jsonPath("$.homeNumber", is(Constans.HOME_NUMBER)))
                .andExpect(jsonPath("$.flatNumber", is(Constans.FLAT_NUMBER)));
    }

    @Test
    public void shouldPassValidationWithCompoundCity() throws Exception {

        address.setCity("Skarżysko-Kamienna");

        mockMvc.perform(
                post(URL_TEMPLATE + "add").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(address)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotPassValidationWhenCityStartsWithLowerCase() throws Exception {

        address.setCity("lublin");

        mockMvc.perform(
                post(URL_TEMPLATE + "add").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(address)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotPassValidationWhenHomeNumberIsToLong() throws Exception {

        address.setHomeNumber("112233");

        mockMvc.perform(
                post(URL_TEMPLATE + "add").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(address)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
