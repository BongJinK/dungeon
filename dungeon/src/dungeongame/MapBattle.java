package dungeongame;

import java.util.ArrayList;

public class MapBattle extends Map implements Easy, Normal, Hard {

	private boolean death;
	private boolean boss;
	private int[] clearCount;

	private UnitHero hero;
	private VillainChitauri chitauri;
	private VillainTerrorist terrorist;
	private VillainFenris fenris;
	private VillainThanos thanos;
	private VillainBinLaden binladen;
	private VillainHela hela;

	public MapBattle() {
		this.death = false;
		this.boss = false;
		this.clearCount = new int[3];
		hero = new UnitHero("30�� ������", 1000, 700, 0);
		chitauri = new VillainChitauri("ġŸ�츮");
		terrorist = new VillainTerrorist("�׷�����Ʈ");
		fenris = new VillainFenris("�渮��");

		thanos = new VillainThanos("Ÿ�뽺", 1500, 70, 2);
		binladen = new VillainBinLaden("���縶 �� ��", 2000, 100, 4);
		hela = new VillainHela("���", 5000, 130);
	}
	
	public UnitHero getHero() {
		return hero;
	}
	
	public void setHero(UnitHero hero) {
		this.hero = hero;
	}

	public boolean isDeath() {
		return this.death;
	}

	public void setDeath(boolean death) {
		this.death = death;
	}

	public boolean isBoss() {
		return this.boss;
	}

	public void setBoss(boolean boss) {
		this.boss = boss;
	}

	public int[] getClearCount() {
		return this.clearCount;
	}
	
	private void setPlayerPosition(int y, int x) {
		hero.setPosY(y);
		hero.setPosX(x);
	}

	private void movedPlayer(int[] before, int[] after) {
		super.setFieldDir(before[0], before[1], Map.CLEAR);
		super.setFieldDir(after[0], after[1], Map.HERO);
	}

	private void searchTarget() {

		for (int i = 0; i < super.getField().length; i++) {
			for (int j = 0; j < super.getField()[0].length; j++) {
				if (super.getFieldDir(i, j) == Map.HERO) {
					setPlayerPosition(i, j);
				} else if (super.getFieldDir(i, j) == Map.THANOS) {
					thanos.setPosY(i);
					thanos.setPosX(j);
				} else if (super.getFieldDir(i, j) == Map.BIN_LADEN) {
					binladen.setPosY(i);
					binladen.setPosX(j);
				} else if (super.getFieldDir(i, j) == Map.HELA) {
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

	@Override
	public void thanosDungeon() {
		super.field = new int[MAP_SIZE][MAP_SIZE];
		super.count = 0;
		super.createMap(THANOS, CHITAURI);

		searchTarget();

		while (true) {
			Main.sb.append("[ Infinity War ]\n");
			Main.write(Main.sb.toString());
			Main.sb.setLength(0);
			super.printEasyMap();
			int[] before = { hero.getPosY(), hero.getPosX() };

			hero.move(Map.MAP_SIZE);

			int[] after = { hero.getPosY(), hero.getPosX() };

			if (super.getFieldDir(after[0], after[1]) == Map.CHITAURI) {
				thanosDungeonInferior(before, after);
				if (death)
					return;

			} else if (super.getFieldDir(after[0], after[1]) == Map.THANOS) {
				fightThanos(before);
				if (death || boss)
					return;

			} else {
				movedPlayer(before, after);
				setPlayerPosition(after[0], after[1]);
			}
		}
	}

	@Override
	public void thanosDungeonInferior(int[] before, int[] after) {
		ArrayList<Villain> party = new ArrayList<Villain>();
		int rNum = Main.random.nextInt(2) + 1;
		for (int i = 0; i < rNum; i++) {
			int count = Main.random.nextInt(3);
			if (count != 0)
				count = 1;
			party.add(new VillainChitauri("ġŸ�츮" + (i + 1), 200, 20, count));
		}

		Main.write(String.format("\n%s ������ ��Ÿ����!!!\n", chitauri.getName()));

		while (true) {
			Main.sb.append(String.format("[%s ������ ���� ]\n", chitauri.getName()));
			Main.sb.append(String.format("1. ���� 2. ȸ��(%d) 3. ����ġ��", hero.getCount()));
			Main.write(Main.sb.toString());
			int choice = Main.selectNumber("");

			if (choice == 1) {
				hero.attack(party.get(rNum - 1));
				if (party.get(rNum - 1).getHp() == 0) {
					Main.write(String.format("%s��(��) �׿����ϴ�.\n", party.get(rNum - 1).getName()));
					getPortionByInferior(party, rNum);

					rNum--;
					if (rNum == 0) {
						Main.write(String.format("%s������ �������� �¸��߽��ϴ�.\n", chitauri.getName()));
						movedPlayer(before, after);
						setPlayerPosition(after[0], after[1]);

						super.count++;
						break;
					}
				}

				party.get(rNum - 1).attack(hero);
				if (hero.getHp() == 0) {
					Main.write(String.format("%s��(��) ����Ͽ� �й��߽��ϴ�.\n", hero.getName()));
					this.death = true;
					return;
				}
			} else if (choice == 2) {
				hero.recovery();
			} else if (choice == 3) {
				Main.write("�����Ķ�!");
				setPlayerPosition(before[0], before[1]);
				break;
			}
		}

	}

	@Override
	public void fightThanos(int[] before) {

		if (super.count == 7) {
			Main.sb.append(String.format("%s��(��) ��Ÿ����!!!\n", thanos.getName()));
			Main.write(Main.sb.toString());

			while (true) {
				Main.sb.append(String.format("[ %s��(��) ���� ]\n", thanos.getName()));
				Main.sb.append("1. ���� 2. ȸ�� 3. ����ġ�� ");
				Main.write(Main.sb.toString());
				int sel = Main.selectNumber("");

				if (sel == 1) {
					hero.attack(thanos);
					if (thanos.getHp() == 0) {
						int count = thanos.getCount();
						hero.setCount(hero.getCount() + count);
						Main.sb.append(String.format("%s��(��) �׿� �¸��߽��ϴ�.", thanos.getName()));
						Main.sb.append(String.format("���� \"%d��\"�� ȹ���߽��ϴ�.\n", count));
						Main.write(Main.sb.toString());
						this.boss = true;
						this.clearCount[0]++;
						return;
					}

					thanos.attack(hero);
					if (hero.getHp() == 0) {
						Main.sb.append(String.format("%s��(��) ����Ͽ� �й��߽��ϴ�.\n", hero.getName()));
						Main.write(Main.sb.toString());
						this.death = true;
						break;
					}
				} else if (sel == 2) {
					hero.recovery();
				} else if (sel == 3) {
					Main.sb.append("�����Ķ�!");
					Main.write(Main.sb.toString());
					setPlayerPosition(before[0], before[1]);
					return;
				}
			}
		} else {
			Main.sb.append("�������� ������ ��� ���� Ŭ����� ���尡���մϴ�.\n");
			Main.sb.append(String.format("���� �� ���� : %d\n", 7 - super.count));
			Main.write(Main.sb.toString());
			super.setFieldDir(before[0], before[1], Map.HERO);
			setPlayerPosition(before[0], before[1]);
		}

	}

	@Override
	public void binladenDungeon() {
		if (this.clearCount[0] < 2) {
			Main.sb.append("Easy���� 2ȸ Ŭ���� �� ���尡���մϴ�.");
			Main.write(Main.sb.toString());
			return;
		}

		super.field = new int[MAP_SIZE_NORMAL][MAP_SIZE_NORMAL];
		super.count = 0;
		super.createMap(BIN_LADEN, CHITAURI, TERRORIST);

		searchTarget();

		while (true) {
			Main.sb.append("[ 9.11 Terror ]");
			Main.write(Main.sb.toString());
			super.printNormalMap();
			int[] before = { hero.getPosY(), hero.getPosX() };

			hero.move(Map.MAP_SIZE_NORMAL);

			int[] after = { hero.getPosY(), hero.getPosX() };

			if (super.getFieldDir(after[0], after[1]) == Map.CHITAURI) {
				thanosDungeonInferior(before, after);
				if (this.death)
					return;

			} else if (super.getFieldDir(after[0], after[1]) == Map.TERRORIST) {
				binladenDungeonInferior(before, after);
				if (this.death || this.boss)
					return;

			} else if (super.getFieldDir(after[0], after[1]) == Map.BIN_LADEN) {
				fightBinLaden(before);
				if (this.death || this.boss)
					return;

			} else {
				movedPlayer(before, after);
				setPlayerPosition(after[0], after[1]);
			}
		}
	}

	@Override
	public void binladenDungeonInferior(int[] before, int[] after) {
		ArrayList<Villain> party = new ArrayList<Villain>();
		int rNum = 1;// Main.random.nextInt(4) + 2;
		for (int i = 0; i < rNum; i++) {
			int count = Main.random.nextInt(3);
			if (count != 0)
				count = 1;
			party.add(new VillainTerrorist("�׷���" + (i + 1), 400, 30, count));
		}

		Main.sb.append(String.format("\n%s ��ü�� ��Ÿ����!!!\n", terrorist.getName()));
		Main.write(Main.sb.toString());

		while (true) {
			Main.sb.append(String.format("[ %s���� ���� ]\n", terrorist.getName()));
			Main.sb.append("1. ���� 2. ȸ�� 3. ����ġ��");
			Main.write(Main.sb.toString());
			int choice = Main.selectNumber("");

			if (choice == 1) {
				hero.attack(party.get(rNum - 1));
				if (party.get(rNum - 1).getHp() == 0) {
					Main.sb.append(String.format("%s��(��) �׿����ϴ�.\n", party.get(rNum - 1).getName()));
					Main.write(Main.sb.toString());
					getPortionByInferior(party, rNum);

					rNum--;
					if (rNum == 0) {
						Main.sb.append(String.format("%s���� ���￡�� �¸��߽��ϴ�.\n", terrorist.getName()));
						Main.write(Main.sb.toString());
						movedPlayer(before, after);
						setPlayerPosition(after[0], after[1]);

						super.count++;
						break;
					}
				}

				party.get(rNum - 1).attack(hero);
				if (hero.getHp() == 0) {
					Main.sb.append(String.format("%s��(��) ����Ͽ� �й��߽��ϴ�.\n", hero.getName()));
					Main.write(Main.sb.toString());
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

	@Override
	public void fightBinLaden(int[] before) {
		if (super.count == 14) {
			Main.sb.append(String.format("%s�� ����϶�!\n", binladen.getName()));
			Main.write(Main.sb.toString());

			while (true) {
				Main.sb.append(String.format("[ Neptune Spear ]\n", binladen.getName()));
				Main.sb.append("1. ���� 2. ȸ�� 3. ����ġ��\n");
				Main.write(Main.sb.toString());
				int sel = Main.selectNumber("");

				if (sel == 1) {
					if (binladen.getHide() == 0) {
						hero.attack(binladen);
						if (binladen.getHp() == 0) {
							Main.sb.append(String.format("%s��(��) �׿� �¸��߽��ϴ�.", binladen.getName()));

							// ���� ȹ�� ����
							int count = binladen.getCount();
							Main.sb.append(String.format("���� \"%d��\"�� ȹ���߽��ϴ�.\n", count));
							hero.setCount(hero.getCount() + count);

							this.boss = true;
							this.clearCount[1]++;
							hero.setDotDamageCount(0);
							Main.write(Main.sb.toString());
							return;
						}
					}

					binladen.attack(hero);
					if (hero.getHp() == 0) {
						Main.sb.append(String.format("%s��(��) ����Ͽ� �й��߽��ϴ�.\n", hero.getName()));
						Main.write(Main.sb.toString());
						this.death = true;
						break;
					}
				} else if (sel == 2) {
					hero.recovery();
				} else if (sel == 3) {
					Main.sb.append("�����Ķ�");
					Main.write(Main.sb.toString());
					setPlayerPosition(before[0], before[1]);
					return;
				}
			}
		} else {
			Main.sb.append("�������� ������ ��� ���� Ŭ����� ���尡���մϴ�.\n");
			Main.sb.append(String.format("���� �� ���� : %d\n", 14 - super.count));
			Main.write(Main.sb.toString());
			super.setFieldDir(before[0], before[1], Map.HERO);
			setPlayerPosition(before[0], before[1]);
		}
	}

	@Override
	public void helaDungeon() {
		if (this.clearCount[1] < 3) {
			Main.sb.append("Normal���� 3ȸ Ŭ���� �� ���尡���մϴ�.\n");
			Main.write(Main.sb.toString());
			return;
		}

		super.field = new int[MAP_SIZE_HARD][MAP_SIZE_HARD];
		super.count = 0;
		super.createMap(HELA, CHITAURI, TERRORIST, FENRIS);

		searchTarget();

		while (true) {
			Main.sb.append("[ Goddess of Death ]\n");
			Main.write(Main.sb.toString());
			super.printHardMap();
			int[] before = { hero.getPosY(), hero.getPosX() };

			hero.move(Map.MAP_SIZE_HARD);

			int[] after = { hero.getPosY(), hero.getPosX() };

			if (super.getFieldDir(after[0], after[1]) == Map.CHITAURI) {
				thanosDungeonInferior(before, after);
				if (this.death)
					return;

			} else if (super.getFieldDir(after[0], after[1]) == Map.TERRORIST) {
				binladenDungeonInferior(before, after);
				if (this.death)
					return;

			} else if (super.getFieldDir(after[0], after[1]) == Map.FENRIS) {
				helaDungeonInferior(before, after);
				if (this.death)
					return;

			} else if (super.getFieldDir(after[0], after[1]) == Map.HELA) {
				fightHela(before);
				if (this.death || this.boss)
					return;

			} else {
				movedPlayer(before, after);
				setPlayerPosition(after[0], after[1]);
			}
		}

	}

	@Override
	public void helaDungeonInferior(int[] before, int[] after) {
		ArrayList<Villain> party = new ArrayList<Villain>();
		int num = 1;
		for (int i = 0; i < num; i++) {
			party.add(new VillainFenris("�渮��" + (i + 1), 600, 50));
		}

		Main.sb.append(String.format("\n%s�� ��Ÿ����!!!\n", fenris.getName()));
		Main.write(Main.sb.toString());

		while (true) {
			Main.sb.append(String.format("[ %s���� ���� ]\n", fenris.getName()));
			Main.sb.append("1. ���� 2. ȸ�� 3. ����ġ��");
			Main.write(Main.sb.toString());
			int choice = Main.selectNumber("");

			if (choice == 1) {
				hero.attack(party.get(num - 1));
				if (party.get(num - 1).getHp() == 0) {
					Main.sb.append(String.format("%s��(��) �׿����ϴ�.\n", party.get(num - 1).getName()));
					Main.write(Main.sb.toString());
					getPortionByInferior(party, num);

					num--;
					if (num == 0) {
						Main.sb.append(String.format("%s���� �������� �¸��߽��ϴ�.\n", fenris.getName()));
						Main.write(Main.sb.toString());
						movedPlayer(before, after);
						setPlayerPosition(after[0], after[1]);

						super.count++;
						break;
					}
				}

				party.get(num - 1).attack(hero);
				if (hero.getHp() == 0) {
					Main.sb.append(String.format("%s��(��) ����Ͽ� �й��߽��ϴ�.\n", hero.getName()));
					Main.write(Main.sb.toString());
					this.death = true;
					return;
				}
			} else if (choice == 2) {
				hero.recovery();
			} else if (choice == 3) {

				Main.sb.append("�����Ķ�");
				Main.write(Main.sb.toString());
				setPlayerPosition(before[0], before[1]);
				break;
			}
		}
	}

	@Override
	public void fightHela(int[] before) {
		if (super.count == 23) {
			Main.sb.append(String.format("%s�� �����Ķ�!\n", hela.getName()));
			Main.write(Main.sb.toString());

			while (true) {
				Main.sb.append(String.format("[ Defend Asgard ]\n", hela.getName()));
				Main.sb.append("1. ���� 2. ȸ�� 3. ����ġ��\n");
				Main.write(Main.sb.toString());
				int sel = Main.selectNumber("");

				if (sel == 1) {
					hero.attack(hela);
					if (hela.getHp() == 0) {
						Main.sb.append(String.format("%s��(��) �׿� �¸��߽��ϴ�.", hela.getName()));

						this.boss = true;
						this.clearCount[2]++;
						hero.setDotDamageCount(0);
						return;
					}

					hela.attack(hero);
					if (hero.getHp() == 0) {
						Main.sb.append(String.format("%s��(��) ����Ͽ� �й��߽��ϴ�.\n", hero.getName()));
						Main.write(Main.sb.toString());
						this.death = true;
						break;
					}
				} else if (sel == 2) {
					hero.recovery();
				} else if (sel == 3) {
					Main.sb.append("�����Ķ�");
					Main.write(Main.sb.toString());
					setPlayerPosition(before[0], before[1]);
					return;
				}
			}
		} else {
			Main.sb.append("�������� ������ ��� ���� Ŭ����� ���尡���մϴ�.\n");
			Main.sb.append(String.format("���� �� ���� : %d\n", 23 - super.count));
			Main.write(Main.sb.toString());
			super.setFieldDir(before[0], before[1], Map.HERO);
			setPlayerPosition(before[0], before[1]);
		}
	}

}
