package com.kakao.card.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.card.api.mapper.SearchMapper;
import com.kakao.card.common.Jasypt;
import com.kakao.card.common.Utils;
import com.kakao.card.data.SearchVo;
import com.kakao.card.data.model.Search;
import com.kakao.card.data.model.SearchInfo;

@Service
public class SearchService {
	
	@Autowired 
	private SearchMapper mapper;
	
	@Autowired
	private Jasypt jasypt;

	public SearchInfo select(SearchVo param, String key, boolean isMask) {
		
		Search info = mapper.select(param);
		if(info == null) {
			return null;
		}
		
		String decCardInfo = jasypt.dec(key, info.getCardInfo());

		SearchInfo rtn = new SearchInfo(info);
		if(isMask) {
			rtn.setCardNo(Utils.maskCardNo(decCardInfo.split("\\|")[0]));
		} else {
			rtn.setCardNo(decCardInfo.split("\\|")[0]);
		}
		rtn.setExpiryDate(decCardInfo.split("\\|")[1]);
		rtn.setCvc(decCardInfo.split("\\|")[2]);
		
		return rtn;
	}
	
	public List<SearchInfo> selectList(SearchVo param, String key, boolean isMask) {
		List<SearchInfo> rtn = new ArrayList<>();
		
		List<Search> list = mapper.selectList(param);
		if(list != null && list.size() > 0) {
			for(Search info : list) {
				String decCardInfo = jasypt.dec(key, info.getCardInfo());

				SearchInfo searchInfo = new SearchInfo(info);
				if(isMask) {
					searchInfo.setCardNo(Utils.maskCardNo(decCardInfo.split("\\|")[0]));
				} else {
					searchInfo.setCardNo(decCardInfo.split("\\|")[0]);
				}
				searchInfo.setExpiryDate(decCardInfo.split("\\|")[1]);
				searchInfo.setCvc(decCardInfo.split("\\|")[2]);
				
				rtn.add(searchInfo);
			}
		}
		
		return rtn;
	}

}
