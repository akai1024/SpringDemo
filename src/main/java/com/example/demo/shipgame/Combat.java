package com.example.demo.shipgame;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.player.Player;
import com.google.gson.Gson;

/**
 * 戰事
 * 
 * @author kai
 *
 */
public class Combat {

	private Gson gson = new Gson();

	private static final int FIELD_LENGTH = 10;
	private static final int FIELD_WIDTH = 10;
	private static final int COMMANDER_SIZE = 2;
	private static final int DEPLOYMENT_SIZE = 3;

	/** 當前回合狀態 */
	private Phase currentPhase = Phase.MATCHING;

	/** 戰場 */
	private HashMap<Player, HashMap<String, OwnGrid>> battleFied = new HashMap<>();

	/** 參與的指揮官 */
	private ArrayList<Player> commanders = new ArrayList<>();

	/** 指揮官與戰船數量 */
	private HashMap<Player, Integer> commanderShips = new HashMap<>();

	/** 指揮官回合 */
	private int currentCommander;

	public Combat() {
		initBattleField();
	}

	public ArrayList<Player> getCommanders() {
		return commanders;
	}

	/**
	 * 切換回合
	 */
	private void switchPhase(Phase phase) {
		this.currentPhase = phase;
	}

	/**
	 * 加入新的指揮官，如果已滿則回傳true
	 */
	public boolean addCommander(Player player) {
		if (!currentPhase.equals(Phase.MATCHING)) {
			return false;
		}

		if (commanders.contains(player)) {
			return false;
		}

		commanders.add(player);

		// 人數足夠就轉移到部屬階段
		if (commanders.size() >= COMMANDER_SIZE) {
			switchPhase(Phase.DEPLOYMENT);
			return true;
		}
		return false;
	}

	/**
	 * 初始化戰場
	 */
	public void initBattleField() {
		if (!currentPhase.equals(Phase.CLEAN_FIELD)) {
			return;
		}

		// 清空
		battleFied.clear();
		commanderShips.clear();

		// 針對指揮官數量建立各方部屬戰場
		for (Player commander : commanders) {
			HashMap<String, OwnGrid> field = new HashMap<>();
			for (int x = 0; x < FIELD_LENGTH; x++) {
				for (int y = 0; y < FIELD_WIDTH; y++) {
					OwnGrid grid = new OwnGrid();
					grid.setX(x);
					grid.setY(y);
					field.put(grid.toString(), grid);
				}
			}
			battleFied.put(commander, field);
		}
	}

	/**
	 * 是否合法的位置
	 */
	private boolean isValidGrid(Grid grid) {
		if (grid == null) {
			return false;
		}

		if (grid.getX() >= FIELD_LENGTH) {
			return false;
		}

		if (grid.getY() >= FIELD_WIDTH) {
			return false;
		}

		return true;
	}

	/**
	 * 玩家部屬自己的船隻
	 */
	public boolean deploy(Player player, String deployment) {
		if (!currentPhase.equals(Phase.DEPLOYMENT)) {
			return false;
		}

		if (commanderShips.containsKey(player)) {
			return false;
		}

		DeployMessage message = gson.fromJson(deployment, DeployMessage.class);
		ArrayList<Grid> grids = message.getDeployment();
		if (grids == null) {
			return false;
		}

		int legalSize = 0;
		HashMap<String, OwnGrid> field = battleFied.get(player);
		for (Grid grid : grids) {
			if (isValidGrid(grid)) {
				String gridKey = grid.toString();
				field.get(gridKey).setDeployed(true);
				legalSize++;
			}
		}

		if (legalSize != DEPLOYMENT_SIZE) {
			return false;
		}

		// 加入部屬完畢的指揮官
		commanderShips.put(player, legalSize);
		if (commanderShips.size() == commanders.size()) {
			switchPhase(Phase.BATTLE);
		}
		return true;
	}

	/**
	 * 開火許可(確認開火回合)
	 */
	private boolean isFirePermision(Player player) {
		return commanders.get(currentCommander).equals(player);
	}

	/**
	 * 開火
	 */
	public boolean fire(Player player, String location) {
		if (!currentPhase.equals(Phase.BATTLE)) {
			return false;
		}

		// 開火確認
		if (!isFirePermision(player)) {
			return false;
		}

		// 先自動指定對方
		Player targetPlayer = null;
		for (Player commander : commanders) {
			if (!commander.equals(player)) {
				targetPlayer = commander;
				break;
			}
		}

		// 砲火位置
		Grid fireLocation = gson.fromJson(location, Grid.class);
		if (!isValidGrid(fireLocation)) {
			return false;
		}

		String gridKey = fireLocation.toString();
		HashMap<String, OwnGrid> targetField = battleFied.get(targetPlayer);
		OwnGrid targetGrid = targetField.get(gridKey);
		int ships = commanderShips.get(targetPlayer);
		// 有部屬
		if (targetGrid.isDeployed()) {
			// 已被摧毀，你已經炸過了為何還對同樣目標下手
			if (targetGrid.isDestoryed()) {
				// 轟炸失敗
				return false;
			}

			targetGrid.setDestoryed(true);
			ships--;
			commanderShips.put(targetPlayer, ships);
			// 目標輸了
			if (ships <= 0) {
				switchPhase(Phase.FINISH);
			}
		}

		turnover();
		return true;
	}

	/**
	 * 行動權替換
	 */
	private void turnover() {
		currentCommander++;
		if (currentCommander >= commanders.size()) {
			currentCommander = 0;
		}
	}

}
