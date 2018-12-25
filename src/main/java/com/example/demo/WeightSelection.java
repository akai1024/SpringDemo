package com.example.demo;

import java.util.Random;
import java.util.TreeMap;

/**
 * 權重選擇器
 */
public class WeightSelection<T> {

	private Random random = new Random();
	private TreeMap<Integer, T> container = new TreeMap<>();
	private int totalWeight = 0;
	private boolean isRemoveAfterSelect = false;
	
	public void setRemoveAfterSelect(boolean isRemoveAfterSelect) {
		this.isRemoveAfterSelect = isRemoveAfterSelect;
	}

	public void add(int weight, T obj) {
		totalWeight += weight;
		container.put(totalWeight, obj);
	}

	public T random() {
		if(totalWeight <= 0) {
			return null;
		}
		
		int rndKey = random.nextInt(totalWeight) + 1;
		int ceilingKey = container.ceilingKey(rndKey);
		if(isRemoveAfterSelect) {
			totalWeight -= ceilingKey;
			return container.remove(ceilingKey);
		}
		
		return container.get(ceilingKey);
	}

}
