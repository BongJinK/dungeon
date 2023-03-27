package dungeongame;

import java.util.ArrayList;

public class Game {

	private boolean death;
	private boolean boss;

	Hero hero;
	Chitauri chitauri;
	Terrorist terrorist;
	Fenris fenris;
	Thanos thanos;
	BinLaden binladen;
	Hela hela;
	Map map;

	private int[] count;

	public Game() {
		this.death = false;
		this.boss = false;
		this.count = new int[3];
		this.count[0] = 2;
		this.count[1] = 3;
		hero = new Hero("���������Ŀ�", 1000, 700, 0);
		chitauri = new Chitauri("ġŸ�츮");
		terrorist = new Terrorist("�׷�����Ʈ");
		fenris = new Fenris("�渮��");

		thanos = new Thanos("Ÿ�뽺", 1500, 70, 2);
		binladen = new BinLaden("���縶 �� ��", 2000, 100, 4);
		hela = new Hela("���", 5000, 130);
	}

	private boolean clearCondition() {
		return this.count[2] == 1;
	}

	private int selectDungeon(String message) {
		System.out.println("1. Easy[Thanos]\n2. Normal[Bin Laden]");
		System.out.println("3. Hard[Hela]\n0. �ڷΰ���");

		int sel = Main.selectNumber(message);
		return sel;
	}

	private void setPlayerPosition(int y, int x) {
		hero.setPosY(y);
		hero.setPosX(x);
	}

	private void movedPlayer(int[] before, int[] after) {
		this.map.setFieldDir(before[0], before[1], Map.CLEAR);
		this.map.setFieldDir(after[0], after[1], Map.HERO);
	}

	private void searchEasyTarget() {
		for (int i = 0; i < this.map.getField().length; i++) {
			for (int j = 0; j < this.map.getField()[0].length; j++) {
				if (this.map.getFieldDir(i, j) == Map.HERO) {
					setPlayerPosition(i, j);
				}
				if (this.map.getFieldDir(i, j) == Map.THANOS) {
					thanos.setPosY(i);
					thanos.setPosX(j);
				}
			}
		}
	}

	private void searchNormalTarget() {
		for (int i = 0; i < this.map.getField().length; i++) {
			for (int j = 0; j < this.map.getField()[0].length; j++) {
				if (this.map.getFieldDir(i, j) == Map.HERO) {
					setPlayerPosition(i, j);
				}
				if (this.map.getFieldDir(i, j) == Map.BIN_LADEN) {
					binladen.setPosY(i);
					binladen.setPosX(j);
				}
			}
		}
	}

	private void searchHardTarget() {
		for (int i = 0; i < this.map.getField().length; i++) {
			for (int j = 0; j < this.map.getField()[0].length; j++) {
				if (this.map.getFieldDir(i, j) == Map.HERO) {
					setPlayerPosition(i, j);
				}
				if (this.map.getFieldDir(i, j) == Map.HELA) {
					hela.setPosY(i);
					hela.setPosX(j);
				}
			}
		}
	}

	private void getPortionByInferior(ArrayList<Villain> party, int index) {
		int count = party.get(index - 1).getCount();
		System.out.printf("���� \"%d��\"�� ȹ���߽��ϴ�.\n", count);

		hero.setCount(hero.getCount() + count);
	}

	// ���� �޼���
	private void clearMessage(String bossname, int number) {
		if (this.boss) {
			System.out.printf("%s���� %dȸ Ŭ���� �Ϸ�\n", bossname, this.count[number]);
			this.boss = false;
		}
	}

	// Ÿ�뽺����
	private void thanosDungeon() {
		this.map = new Map(Map.MAP_SIZE);
		this.map.createMap(Map.THANOS, Map.CHITAURI);

		searchEasyTarget();

		while (true) {
			System.out.println("[ Infinity War ]");
			this.map.printEasyMap();
			int[] before = { hero.getPosY(), hero.getPosX() };

			hero.move(Map.MAP_SIZE);

			int[] after = { hero.getPosY(), hero.getPosX() };

			if (this.map.getFieldDir(after[0], after[1]) == Map.CHITAURI) {
				thanosDungeonInferior(before, after);
				if (this.death)
					return;

			} else if (this.map.getFieldDir(after[0], after[1]) == Map.THANOS) {
				fightThanos(before);
				if (this.death || this.boss)
					return;

			} else {
				movedPlayer(before, after);
				setPlayerPosition(after[0], after[1]);
			}
		}
	}

	private void thanosDungeonInferior(int[] before, int[] after) {
		ArrayList<Villain> party = new ArrayList<Villain>();
		int rNum = Main.random.nextInt(4) + 2;
		for (int i = 0; i < rNum; i++) {
			int count = Main.random.nextInt(3);
			if (count != 0)
				count = 1;
			party.add(new Chitauri("ġŸ�츮" + (i + 1), 200, 20, count));
		}

		System.err.printf("\n%s ������ ��Ÿ����!!!\n", chitauri.getName());

		while (true) {
			System.out.printf("[%s ������ ���� ]\n", chitauri.getName());
			System.out.println("1. ���� 2. ȸ�� 3. ����ġ��");
			int choice = Main.selectNumber("");

			if (choice == 1) {
				hero.attack(party.get(rNum - 1));
				if (party.get(rNum - 1).getHp() == 0) {
					System.out.printf("%s��(��) �׿����ϴ�.\n", party.get(rNum - 1).getName());
					getPortionByInferior(party, rNum);

					rNum--;
					if (rNum == 0) {
						System.out.printf("%s������ �������� �¸��߽��ϴ�.\n", chitauri.getName());
						movedPlayer(before, after);
						setPlayerPosition(after[0], after[1]);

						this.map.setCount(this.map.getCount() + 1);
						break;
					}
				}

				party.get(rNum - 1).attack(hero);
				if (hero.getHp() == 0) {
					System.out.printf("%s��(��) ����Ͽ� �й��߽��ϴ�.", hero.getName());
					this.death = true;
					return;
				}
			} else if (choice == 2) {
				hero.recovery();
			} else if (choice == 3) {
				System.out.println("�����Ķ�!");
				setPlayerPosition(before[0], before[1]);
				break;
			}
		}
	}

	private void fightThanos(int[] before) {
		if (this.map.getCount() == 7) {
			System.out.printf("%s��(��) ��Ÿ����!!!\n", thanos.getName());

			while (true) {
				System.out.printf("[ %s��(��) ���� ]\n", thanos.getName());
				System.out.println("1. ���� 2. ȸ�� 3. ����ġ��");
				int sel = Main.selectNumber("");

				if (sel == 1) {
					hero.attack(thanos);
					if (thanos.getHp() == 0) {
						System.out.printf("%s��(��) �׿� �¸��߽��ϴ�.", thanos.getName());

						int count = thanos.getCount();
						System.out.printf("���� \"%d��\"�� ȹ���߽��ϴ�.\n", count);
						hero.setCount(hero.getCount() + count);

						this.boss = true;
						this.count[0]++;
						return;
					}

					thanos.attack(hero);
					if (hero.getHp() == 0) {
						System.out.printf("%s��(��) ����Ͽ� �й��߽��ϴ�.", hero.getName());
						this.death = true;
						break;
					}
				} else if (sel == 2) {
					hero.recovery();
				} else if (sel == 3) {
					System.out.println("�����Ķ�!");
					setPlayerPosition(before[0], before[1]);
					return;
				}
			}
		} else {
			System.out.println("�������� ������ ��� ���� Ŭ����� ���尡���մϴ�.");
			System.out.printf("���� �� ���� : %d\n", 7 - this.map.getCount());
			this.map.setFieldDir(before[0], before[1], Map.HERO);
			setPlayerPosition(before[0], before[1]);
		}
	}

	// ��� ����
	private void binladenDungeon() {
		if (this.count[0] < 2) {
			System.err.println("Easy���� 2ȸ Ŭ���� �� ���尡���մϴ�.");
			return;
		}

		this.map = new Map(Map.MAP_SIZE_NORMAL);
		this.map.createMap(Map.BIN_LADEN, Map.CHITAURI, Map.TERRORIST);

		searchNormalTarget();

		while (true) {
			System.out.println("[ 9.11 Terror ]");
			this.map.printNormalMap();
			int[] before = { hero.getPosY(), hero.getPosX() };

			hero.move(Map.MAP_SIZE_NORMAL);

			int[] after = { hero.getPosY(), hero.getPosX() };

			if (this.map.getFieldDir(after[0], after[1]) == Map.CHITAURI) {
				thanosDungeonInferior(before, after);
				if (this.death)
					return;

			} else if (this.map.getFieldDir(after[0], after[1]) == Map.TERRORIST) {
				binladenDungeonInferior(before, after);
				if (this.death || this.boss)
					return;

			} else if (this.map.getFieldDir(after[0], after[1]) == Map.BIN_LADEN) {
				fightBinLaden(before);
				if (this.death || this.boss)
					return;

			} else {
				movedPlayer(before, after);
				setPlayerPosition(after[0], after[1]);
			}
		}
	}

	private void binladenDungeonInferior(int[] before, int[] after) {
		ArrayList<Villain> party = new ArrayList<Villain>();
		int rNum = Main.random.nextInt(4) + 2;
		for (int i = 0; i < rNum; i++) {
			int count = Main.random.nextInt(3);
			if (count != 0)
				count = 1;
			party.add(new Terrorist("�׷���" + (i + 1), 400, 30, count));
		}

		System.err.printf("\n%s ��ü�� ��Ÿ����!!!\n", terrorist.getName());

		while (true) {
			System.out.printf("[ %s���� ���� ]\n", terrorist.getName());
			System.out.println("1. ���� 2. ȸ�� 3. ����ġ��");
			int choice = Main.selectNumber("");

			if (choice == 1) {
				hero.attack(party.get(rNum - 1));
				if (party.get(rNum - 1).getHp() == 0) {
					System.out.printf("%s��(��) �׿����ϴ�.\n", party.get(rNum - 1).getName());
					getPortionByInferior(party, rNum);

					rNum--;
					if (rNum == 0) {
						System.out.printf("%s���� ���￡�� �¸��߽��ϴ�.\n", terrorist.getName());
						movedPlayer(before, after);
						setPlayerPosition(after[0], after[1]);

						this.map.setCount(this.map.getCount() + 1);
						break;
					}
				}

				party.get(rNum - 1).attack(hero);
				if (hero.getHp() == 0) {
					System.out.printf("%s��(��) ����Ͽ� �й��߽��ϴ�.", hero.getName());
					this.death = true;
					return;
				}
			} else if (choice == 2) {
				hero.recovery();
			} else if (choice == 3) {
				System.out.println("�����Ķ�!");
				setPlayerPosition(before[0], before[1]);
				break;
			}
		}
	}

	private void fightBinLaden(int[] before) {
		if (this.map.getCount() == 14) {
			System.out.printf("%s�� ����϶�!\n", binladen.getName());

			while (true) {
				System.out.printf("[ Neptune Spear ]\n", binladen.getName());
				System.out.println("1. ���� 2. ȸ�� 3. ����ġ��");
				int sel = Main.selectNumber("");

				if (sel == 1) {
					if (binladen.getHide() == 0) {
						hero.attack(binladen);
						if (binladen.getHp() == 0) {
							System.out.printf("%s��(��) �׿� �¸��߽��ϴ�.", binladen.getName());

							// ���� ȹ�� ����
							int count = binladen.getCount();
							System.out.printf("���� \"%d��\"�� ȹ���߽��ϴ�.\n", count);
							hero.setCount(hero.getCount() + count);

							this.boss = true;
							this.count[1]++;
							hero.setDotDamageCount(0);
							return;
						}
					}

					binladen.attack(hero);
					if (hero.getHp() == 0) {
						System.out.printf("%s��(��) ����Ͽ� �й��߽��ϴ�.", hero.getName());
						this.death = true;
						break;
					}
				} else if (sel == 2) {
					hero.recovery();
				} else if (sel == 3) {
					System.out.println("�����Ķ�!");
					setPlayerPosition(before[0], before[1]);
					return;
				}
			}
		} else {
			System.out.println("�������� ������ ��� ���� Ŭ����� ���尡���մϴ�.");
			System.out.printf("���� �� ���� : %d\n", 14 - this.map.getCount());
			this.map.setFieldDir(before[0], before[1], Map.HERO);
			setPlayerPosition(before[0], before[1]);
		}
	}

	// ��� ����
	private void helaDungeon() {
		if (this.count[1] < 3) {
			System.err.println("Normal���� 3ȸ Ŭ���� �� ���尡���մϴ�.");
			return;
		}

		this.map = new Map(Map.MAP_SIZE_HARD);
		this.map.createMap(Map.HELA, Map.CHITAURI, Map.TERRORIST, Map.FENRIS);

		System.out.println("���� ���� �� Ȯ����");

		searchHardTarget();

		while (true) {
			System.out.println("[ Goddess of Death ]");
			this.map.printHardMap();
			int[] before = { hero.getPosY(), hero.getPosX() };

			hero.move(Map.MAP_SIZE_HARD);

			int[] after = { hero.getPosY(), hero.getPosX() };

			if (this.map.getFieldDir(after[0], after[1]) == Map.CHITAURI) {
				thanosDungeonInferior(before, after);
				if (this.death)
					return;

			} else if (this.map.getFieldDir(after[0], after[1]) == Map.TERRORIST) {
				binladenDungeonInferior(before, after);
				if (this.death || this.boss)
					return;

			} else if (this.map.getFieldDir(after[0], after[1]) == Map.FENRIS) {
				helaDungeonInferior(before, after);
				if (this.death || this.boss)
					return;

			} else if (this.map.getFieldDir(after[0], after[1]) == Map.BIN_LADEN) {
				fightHela(before);
				if (this.death || this.boss)
					return;

			} else {
				movedPlayer(before, after);
				setPlayerPosition(after[0], after[1]);
			}
		}

	}

	private void helaDungeonInferior(int[] before, int[] after) {
		ArrayList<Villain> party = new ArrayList<Villain>();
		int num = 3;
		for (int i = 0; i < num; i++) {
			party.add(new Fenris("�渮��" + (i + 1), 600, 50));
		}

		System.err.printf("\n%s�� ��Ÿ����!!!\n", fenris.getName());

		while (true) {
			System.out.printf("[ %s���� ���� ]\n", fenris.getName());
			System.out.println("1. ���� 2. ȸ�� 3. ����ġ��");
			int choice = Main.selectNumber("");

			if (choice == 1) {
				hero.attack(party.get(num - 1));
				if (party.get(num - 1).getHp() == 0) {
					System.out.printf("%s��(��) �׿����ϴ�.\n", party.get(num - 1).getName());
					getPortionByInferior(party, num);

					num--;
					if (num == 0) {
						System.out.printf("%s���� �������� �¸��߽��ϴ�.\n", fenris.getName());
						movedPlayer(before, after);
						setPlayerPosition(after[0], after[1]);

						this.map.setCount(this.map.getCount() + 1);
						break;
					}
				}

				party.get(num - 1).attack(hero);
				if (hero.getHp() == 0) {
					System.out.printf("%s��(��) ����Ͽ� �й��߽��ϴ�.", hero.getName());
					this.death = true;
					return;
				}
			} else if (choice == 2) {
				hero.recovery();
			} else if (choice == 3) {
				System.out.println("�����Ķ�!");
				setPlayerPosition(before[0], before[1]);
				break;
			}
		}
	}

	private void fightHela(int[] before) {
		if (this.map.getCount() == 23) {
			System.out.printf("%s�� �����Ķ�!\n", hela.getName());

			while (true) {
				System.out.println("[ Defend Asgard ]");
				System.out.println("1. ���� 2. ȸ�� 3. ����ġ��");
				int sel = Main.selectNumber("");

				if (sel == 1) {
					hero.attack(hela);
					if (hela.getHp() == 0) {
						System.out.printf("%s��(��) �׿� �¸��߽��ϴ�.", hela.getName());

						this.boss = true;
						this.count[2]++;
						hero.setDotDamageCount(0);
						return;
					}

					hela.attack(hero);
					if (hero.getHp() == 0) {
						System.out.printf("%s��(��) ����Ͽ� �й��߽��ϴ�.", hero.getName());
						this.death = true;
						break;
					}
				} else if (sel == 2) {
					hero.recovery();
				} else if (sel == 3) {
					System.out.println("�����Ķ�!");
					setPlayerPosition(before[0], before[1]);
					return;
				}
			}
		} else {
			System.out.println("�������� ������ ��� ���� Ŭ����� ���尡���մϴ�.");
			System.out.printf("���� �� ���� : %d\n", 23 - this.map.getCount());
			this.map.setFieldDir(before[0], before[1], Map.HERO);
			setPlayerPosition(before[0], before[1]);
		}

	}

	private void dungeonRun() {
		int sel = selectDungeon("���� ����");

		if (sel == 0)
			System.out.println("�ʱ�ȭ������ ���ư��ϴ�.");

		if (sel == 1) {
			thanosDungeon();
			clearMessage("Thanos", 0);

		} else if (sel == 2) {
			binladenDungeon();
			clearMessage("Bin Laden", 1);

		} else if (sel == 3) {
			helaDungeon();
			clearMessage("Hela", 2);
		}
	}

	public void run() {
		while (true) {
			System.out.println("[ ���� �νñ� ]");
			System.out.println("1. ����\n0. ��������");
			int select = Main.selectNumber("�޴�");

			if (select == 0) {
				System.out.println("������ �����մϴ�.");
				return;
			}

			if (select == 1) {
				dungeonRun();
			}

			if (clearCondition()) {
				System.out.println("Hard���� Ŭ���� �Ϸ� ������ �����մϴ�.");
				break;
			}
		}
	}

}
