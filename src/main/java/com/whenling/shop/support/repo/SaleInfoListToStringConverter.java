package com.whenling.shop.support.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeConverter;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.whenling.shop.entity.Product.SaleInfo;

public class SaleInfoListToStringConverter implements AttributeConverter<List<SaleInfo>, String> {

	@Override
	public String convertToDatabaseColumn(List<SaleInfo> attribute) {
		if (attribute == null) {
			return null;
		}

		return Joiner.on("#@#").join(Lists.transform(attribute, saleInfo -> {
			if (saleInfo == null)
				return "";
			return saleInfo.getContent() + "$@$" + saleInfo.getSortNo();
		}));
	}

	@Override
	public List<SaleInfo> convertToEntityAttribute(String dbData) {
		if (Strings.isNullOrEmpty(dbData)) {
			return new ArrayList<>();
		}
		List<SaleInfo> result = new ArrayList<>();
		for (String saleInfoString : Splitter.on("#@#").split(dbData)) {
			if (!Strings.isNullOrEmpty(saleInfoString)) {
				String[] data = StringUtils.splitByWholeSeparator(saleInfoString, "$@$");
				SaleInfo saleInfo = new SaleInfo();
				saleInfo.setContent(data[0]);
				if (!Strings.isNullOrEmpty(data[1])) {
					saleInfo.setSortNo(Integer.valueOf(data[1]));
				}
				result.add(saleInfo);
			}
		}
		return result;
	}

}
