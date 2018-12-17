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
		int afterLevel = beforeLevel + exp / EXP_PER_LEVEL;

		int expInLevel = exp % EXP_PER_LEVEL;
		int beforeExp = player.getExperience();
		int afterExp = beforeExp + expInLevel;

		player.setLevel(afterLevel);
		player.setExperience(afterExp);

		System.out.println(
				player.toString() + " add exp:" + exp + 
				" from level/exp:" +
				beforeLevel + "/" + beforeExp +
				" to " +
				afterLevel + "/" + afterExp);
	}

}
