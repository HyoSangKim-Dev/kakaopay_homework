package com.kakao.card.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.card.api.mapper.CardMapper;
import com.kakao.card.common.Utils;
import com.kakao.card.data.model.PaymentInfo;

@Service
public class CardService {
	
	@Autowired 
	private CardMapper mapper;
	
	/**
	 * 카드사 전문 전송
	 *   - 실제 전송은 하지않고 테이블에 저장하는 것으로 대체
	 *   
	 * @param param
	 * @return
	 */
	public String send(PaymentInfo param) {
		
		String sendData = createSendText(param);
		
		// 전문생성
		param.setSendData(sendData);
		
		// 전송로직
		// 전송은 무조건 성공했다는 가정
		param.setStatus("OK");
		
		mapper.insert(param);
		
		return sendData;
	}
	
	private String createSendText(PaymentInfo param) {
		StringBuffer sb = new StringBuffer();
		
		// 공통
		sb.append(Utils.getFormat(param.getPayGb(), 10, "LS"))		// 데이터 구분(문자)
		  .append(Utils.getFormat(param.getTradeId(), 20, "LS"));	// 관리번호(문자)
		
		// 바디
		sb.append(Utils.getFormat(param.getCardNo(), 20, "LS"))		// 카드번호(숫자L)
		  .append(Utils.getFormat(param.getMonthIns(), 2, "RZ"))	// 할부계원수(숫자0)
		  .append(Utils.getFormat(param.getExpiryDate(), 4, "LS"))	// 카드유효기(숫자L)
		  .append(Utils.getFormat(param.getCvc(), 3, "LS"))			// cvc(숫자L)
		  .append(Utils.getFormat(param.getPrice(), 10, "RS"))		// 거래금액(숫자)
		  .append(Utils.getFormat(param.getVat(), 10, "RZ"))		// 부가가치세(숫자0)
		  .append(Utils.getFormat(param.getOrgTradeId(), 20, "LS"))	// 원거래 관리번호(문자)
		  .append(Utils.getFormat(param.getCardInfo(), 300, "LS"))	// 함호화된 카드정보(문자)
		  .append(Utils.getFormat("", 47, "LS"))					// 예비필드(문자)
		;
		return Utils.getFormat(sb.length(), 4, "RS") + sb.toString();
	}

}