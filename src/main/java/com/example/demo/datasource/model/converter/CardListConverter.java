package com.example.demo.datasource.model.converter;

import java.util.ArrayList;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.example.demo.CommonUtil;
import com.example.demo.cardgame.Card;
import com.google.gson.reflect.TypeToken;

/**
 * 提供CreatureModel的skillCards這個field轉換
 */
@Converter(autoApply = true)
public class CardListConverter implements AttributeConverter<ArrayList<Card>, String> {
	
	@Override
	public String convertToDatabaseColumn(ArrayList<Card> attribute) {
		return CommonUtil.toJsonStr(attribute);
	}

	@Override
	public ArrayList<Card> convertToEntityAttribute(String dbData) {
		return CommonUtil.parseJson(dbData, new TypeToken<ArrayList<Card>>() {}.getType());
	}

}
