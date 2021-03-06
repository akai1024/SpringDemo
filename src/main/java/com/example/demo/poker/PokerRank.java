package com.example.demo.poker;

/**
 * 撲克牌點數
 */
public enum PokerRank {

	ACE(0, 1),
	II(12, 2),
	III(11, 3),
	IV(10, 4),
	V(9, 5),
	VI(8, 6),
	VII(7, 7),
	VIII(6, 8),
	IX(5, 9),
	X(4, 10),
	JACK(3, 11),
	QUEEN(2, 12),
	KING(1, 13);

	private int order;
	private int number;

	private PokerRank(int order, int number) {
		this.order = order;
		this.number = number;
	}

	/**
	 * 以牌面意義大小牌序
	 */
	public int compareOrder(PokerRank pokerRank) {
		if (pokerRank == null) {
			return 0;
		}

		if (order < pokerRank.order) {
			return 1;
		} else if (order > pokerRank.order) {
			return -1;
		}

		return 0;
	}

	/**
	 * 以牌面數字大小牌序
	 */
	public int compareNumber(PokerRank pokerRank) {
		if (pokerRank == null) {
			return 0;
		}

		return Integer.compare(number, pokerRank.number);
	}

	public int getOrder() {
		return order;
	}

	public int getNumber() {
		return number;
	}

	/**
	 * 取得下一個rank
	 */
	public PokerRank getNexRank() {
		switch (this) {
		case ACE: return II;
		case II: return III;
		case III: return IV;
		case IV: return V;
		case V: return VI;
		case VI: return VII;
		case VII: return VIII;
		case VIII: return IX;
		case IX: return X;
		case X: return JACK;
		case JACK: return QUEEN;
		case QUEEN: return KING;
		case KING: return ACE;

		default:
			return null;
		}
	}

}
