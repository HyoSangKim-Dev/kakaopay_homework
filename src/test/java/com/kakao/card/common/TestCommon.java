package com.kakao.card.common;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.card.data.CancelVo;
import com.kakao.card.data.PaymentVo;
import com.kakao.card.data.SearchVo;
import com.kakao.card.data.model.Payment;
import com.kakao.card.data.model.SearchInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TestCommon {
	
	/**
	 * 출처 : https://jojoldu.tistory.com/478
	 *      https://gofnrk.tistory.com/74
	 */
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext ctx;
	
    @Value("${jasypt.key}")
	private String key;
    
    @BeforeEach
	private void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
			.addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
			//.alwaysDo(print())
			.build();
	}

    
	public String approved(PaymentVo params) throws Throwable {
		String tradeId = "";
		
    	MvcResult result = mockMvc.perform(post("/api/payment/approved")
    			.queryParams(paramConvert(params)))
    	        .andExpect(status().isOk())
    	        //.andDo(print())
    	        .andReturn();
    	
    	Object rtn = resultConvert(result.getResponse().getContentAsString(), Payment.class);
    	
    	if(rtn instanceof Payment) {
    		// 성공
    		tradeId = ((Payment) rtn).getTradeId();
    		System.out.println("결제 성공 ==> " + tradeId);
    		
    	} else {
    		// 실패
    		System.out.println("결제 실패 ==> " + rtn);
    		
    	}
    	
        assertNotNull(result);
        
        return tradeId;
    }
	
	/**
     * 취소 테스트
     * @throws Throwable
     */
    public String cancel(CancelVo params) throws Throwable {
    	String tradeId = "";
    	
    	MvcResult result = mockMvc.perform(post("/api/payment/cancel")
    			.queryParams(paramConvert(params)))
    	        .andExpect(status().isOk())
    	        //.andDo(print())
    	        .andReturn();
    	
    	Object rtn = resultConvert(result.getResponse().getContentAsString(), Payment.class);
    	
    	if(rtn instanceof Payment) {
    		// 성공
    		tradeId = ((Payment) rtn).getTradeId();
    		System.out.println("취소 성공 ==> " + ((Payment) rtn).getTradeId());
    	} else {
    		// 실패
    		System.out.println("취소 실패 ==> " + rtn);
    	}
    	
        assertNotNull(result);
        
        return tradeId;
    }
    
    /**
     * 조회 테스트
     * @throws Throwable
     */
    public void search(SearchVo params) throws Throwable {    	
    	MvcResult result = mockMvc.perform(get("/api/payment/search")
    			.queryParams(paramConvert(params)))
    	        .andExpect(status().isOk())
    	        //.andDo(print())
    	        .andReturn();
    	
    	Object rtn = resultConvert(result.getResponse().getContentAsString(), SearchInfo.class);
    	
    	if(rtn instanceof SearchInfo) {
    		// 성공
    		System.out.println("조회 성공 ==> " + ((SearchInfo) rtn).toString());
    	} else {
    		// 실패
    		System.out.println("조회 실패 ==> " + rtn);
    	}
    	
        assertNotNull(result);
    }

	public static MultiValueMap<String, String> paramConvert(Object dto) { // (2)
		ObjectMapper objectMapper = new ObjectMapper();
		
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            Map<String, String> map = objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {}); // (3)
            params.setAll(map); // (4)

            return params;
        } catch (Exception e) {
            log.error("테스트 파라메터 변환중 오류 발생");
            throw new IllegalStateException("테스트 파라메터 변환중 오류 발생");
        }
    }
	
	public <T> Object resultConvert(String content, Class<?> successClass) {
		ObjectMapper objectMapper = new ObjectMapper();
		
        try {
            return objectMapper.readValue(content, successClass);
        } catch (Exception e) {
        	return content;
        }
    }
	
}
