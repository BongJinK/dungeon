package dungeongame;

public class Map {

	static final int MAP_SIZE = 3;
	static final int MAP_SIZE_NORMAL = 4;
	static final int MAP_SIZE_HARD = 5;

	static final int HERO = 77;

	static final int CHITAURI = 66;
	static final int TERRORIST = 91;
	static final int FENRIS = 500;

	static final int THANOS = 666;
	static final int BIN_LADEN = 911;
	static final int HELA = 5000;
	static final int CLEAR = 777;

	private int[][] field;
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int[][] getField() {
		return field;
	}

	public int getFieldDir(int y, int x) {
		return field[y][x];
	}

	public void setFieldDir(int y, int x, int player) {
		this.field[y][x] = player;
	}

	public Map(int size) {
		this.field = new int[size][size];
		this.count = 0;
	}

	private void topEdge(int size) {
		System.out.print("忙");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print("式");
			}
			if (i != size - 1)
				System.out.print("成");
		}
		System.out.println("忖");
	}

	private void bottomEdge(int size) {
		System.out.print("戌");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print("式");
			}
			if (i != size - 1)
				System.out.print("扛");
		}
		System.out.println("戌");
	}

	public void printEasyMap() {
		topEdge(MAP_SIZE);
		for (int i = 0; i < MAP_SIZE; i++) {
			System.out.print("弛");
			for (int j = 0; j < MAP_SIZE; j++) {
				if (this.field[i][j] == HERO)
					System.out.print("  P  ");
				else if (this.field[i][j] == CHITAURI)
					System.out.print("  C  ");
				else if (this.field[i][j] == THANOS)
					System.out.print("  B  ");
				else if (this.field[i][j] == CLEAR)
					System.out.print("     ");

				if (j != MAP_SIZE - 1)
					System.out.print(" ");
			}
			System.out.println("弛");
			if (i != MAP_SIZE - 1)
				System.out.println("戍式式 式式托式式 式式托式式 式式扣");
		}
		bottomEdge(MAP_SIZE);
	}

	public void printNormalMap() {
		topEdge(MAP_SIZE_NORMAL);
		for (int i = 0; i < MAP_SIZE_NORMAL; i++) {
			System.out.print("弛");
			for (int j = 0; j < MAP_SIZE_NORMAL; j++) {
				if (this.field[i][j] == HERO)
					System.out.print("  P  ");
				else if (this.field[i][j] == CHITAURI)
					System.out.print("  C  ");
				else if (this.field[i][j] == TERRORIST)
					System.out.print("  T  ");
				else if (this.field[i][j] == BIN_LADEN)
					System.out.print("  B  ");
				else if (this.field[i][j] == CLEAR)
					System.out.print("     ");

				if (j != MAP_SIZE_NORMAL - 1)
					System.out.print(" ");
			}
			System.out.println("弛");
			if (i != MAP_SIZE_NORMAL - 1)
				System.out.println("戍式式 式式托式式 式式托式式 式式托式式 式式扣");
		}
		bottomEdge(MAP_SIZE_NORMAL);
	}

	public void printHardMap() {
		topEdge(MAP_SIZE_HARD);
		for (int i = 0; i < MAP_SIZE_HARD; i++) {
			System.out.print("弛");
			for (int j = 0; j < MAP_SIZE_HARD; j++) {
				if (this.field[i][j] == HERO)
					System.out.print("  P  ");
				else if (this.field[i][j] == CHITAURI)
					System.out.print("  C  ");
				else if (this.field[i][j] == TERRORIST)
					System.out.print("  T  ");
				else if (this.field[i][j] == FENRIS)
					System.out.print("  F  ");
				else if (this.field[i][j] == HELA)
					System.out.print("  B  ");
				else if (this.field[i][j] == CLEAR)
					System.out.print("     ");

				if (j != MAP_SIZE_HARD - 1)
					System.out.print(" ");
			}
			System.out.println("弛");
			if (i != MAP_SIZE_HARD - 1)
				System.out.println("戍式式 式式托式式 式式托式式 式式托式式 式式托式式 式式扣");
		}
		bottomEdge(MAP_SIZE_HARD);
	}

	public void createMap(int boss, int inferior) {
		createUnit(this.field, HERO);
		createUnit(this.field, boss);

		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				if (this.field[i][j] == 0) {
					this.field[i][j] = inferior;
				}
			}
		}
	}

	public void createMap(int boss, int inferior, int inferior2) {
		createUnit(this.field, HERO);
		createUnit(this.field, boss);

		int countInferior = 6;
		int countInferior1 = 8;

		for (int i = 0; i < MAP_SIZE_NORMAL; i++) {
			for (int j = 0; j < MAP_SIZE_NORMAL; j++) {
				if (this.field[i][j] == 0) {
					int random = Main.random(2, 0);
					if (random == 0 && countInferior != 0) {
						this.field[i][j] = inferior;
						countInferior--;
					} else if (random == 1 && countInferior1 != 0) {
						this.field[i][j] = inferior2;
						countInferior1--;
					} else {
						j--;
					}
				}
			}
		}
	}

	public void createMap(int boss, int inferior, int inferior2, int inferior3) {
		createUnit(this.field, HERO);
		createUnit(this.field, boss);

		int countInferior = 5;
		int countInferior1 = 8;
		int countInferior2 = 10;

		for (int i = 0; i < MAP_SIZE_HARD; i++) {
			for (int j = 0; j < MAP_SIZE_HARD; j++) {
				int random = Main.random(3, 0);
				if (random == 0 && countInferior != 0) {
					this.field[i][j] = inferior;
					System.out.println(0);
					countInferior--;
				} else if (random == 1 && countInferior1 != 0) {
					this.field[i][j] = inferior2;
					System.out.println(1);
					countInferior1--;
				} else if (random == 2 && countInferior2 != 0) {
					this.field[i][j] = inferior3;
					System.out.println(2);
					countInferior2--;
				} else {
					j--;
				}
			}
		}
	}

	public void createUnit(int[][] map, int target) {
		while (true) {
			int numY = Main.random.nextInt(MAP_SIZE);
			int numX = Main.random.nextInt(MAP_SIZE);

			if (map[numY][numX] == 0) {
				map[numY][numX] = target;
				break;
			}
		}
	}
}
