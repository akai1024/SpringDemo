package com.example.demo.player;

public class LevelCalculator {

	private static final int EXP_PER_LEVEL = 100;

	/**
	 * 加經驗
	 */
	public static void addPlayerExp(Player player, int exp) {
		if (exp <= 0) {
			return;
		}

		int beforeLevel = player.getLevel();
		int beforeExp = player.getExperience();
		int afterExp = beforeExp + exp;
		int addLevel = 0;
		while (afterExp >= EXP_PER_LEVEL) {
			addLevel ++;
			afterExp -= EXP_PER_LEVEL;
		}
		int afterLevel = beforeLevel + addLevel;
		
		player.setLevel(afterLevel);
		player.setExperience(afterExp);

		System.out.println(player.toString() + " add exp:" + exp + " from level/exp:" + beforeLevel + "/" + beforeExp
				+ " to " + afterLevel + "/" + afterExp);
	}

}
