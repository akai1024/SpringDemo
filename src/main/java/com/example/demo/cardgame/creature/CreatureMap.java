package com.example.demo.cardgame.creature;

import java.util.HashMap;

public class CreatureMap {

	private static HashMap<Integer, String> map = new HashMap<>();

	static {

		map.put(0, "com.example.demo.cardgame.creature.Knight");
		map.put(1, "com.example.demo.cardgame.creature.Warrior");
		map.put(2, "com.example.demo.cardgame.creature.Wizzard");

	}

	public static Creature getCreatureInstance(int id) {
		try {
			String clazzStr = map.get(id);
			Class<?> clazz = Class.forName(clazzStr);
			return (Creature) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
