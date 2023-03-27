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
		hero = new Hero("목어깨가아파요", 1000, 700, 0);
		chitauri = new Chitauri("치타우리");
		terrorist = new Terrorist("테러리스트");
		fenris = new Fenris("펜리스");

		thanos = new Thanos("타노스", 1500, 70, 2);
		binladen = new BinLaden("오사마 빈 라덴", 2000, 100, 4);
		hela = new Hela("헬라", 5000, 130);
	}

	private boolean clearCondition() {
		return this.count[2] == 1;
	}

	private int selectDungeon(String message) {
		System.out.println("1. Easy[Thanos]\n2. Normal[Bin Laden]");
		System.out.println("3. Hard[Hela]\n0. 뒤로가기");

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
		System.out.printf("포션 \"%d개\"를 획득했습니다.\n", count);

		hero.setCount(hero.getCount() + count);
	}

	// 공용 메세지
	private void clearMessage(String bossname, int number) {
		if (this.boss) {
			System.out.printf("%s던전 %d회 클리어 완료\n", bossname, this.count[number]);
			this.boss = false;
		}
	}

	// 타노스던전
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
			party.add(new Chitauri("치타우리" + (i + 1), 200, 20, count));
		}

		System.err.printf("\n%s 무리가 나타났다!!!\n", chitauri.getName());

		while (true) {
			System.out.printf("[%s 무리와 전투 ]\n", chitauri.getName());
			System.out.println("1. 공격 2. 회복 3. 도망치기");
			int choice = Main.selectNumber("");

			if (choice == 1) {
				hero.attack(party.get(rNum - 1));
				if (party.get(rNum - 1).getHp() == 0) {
					System.out.printf("%s을(를) 죽였습니다.\n", party.get(rNum - 1).getName());
					getPortionByInferior(party, rNum);

					rNum--;
					if (rNum == 0) {
						System.out.printf("%s무리와 전투에서 승리했습니다.\n", chitauri.getName());
						movedPlayer(before, after);
						setPlayerPosition(after[0], after[1]);

						this.map.setCount(this.map.getCount() + 1);
						break;
					}
				}

				party.get(rNum - 1).attack(hero);
				if (hero.getHp() == 0) {
					System.out.printf("%s이(가) 사망하여 패배했습니다.", hero.getName());
					this.death = true;
					return;
				}
			} else if (choice == 2) {
				hero.recovery();
			} else if (choice == 3) {
				System.out.println("도망쳐라!");
				setPlayerPosition(before[0], before[1]);
				break;
			}
		}
	}

	private void fightThanos(int[] before) {
		if (this.map.getCount() == 7) {
			System.out.printf("%s이(가) 나타났다!!!\n", thanos.getName());

			while (true) {
				System.out.printf("[ %s와(과) 혈투 ]\n", thanos.getName());
				System.out.println("1. 공격 2. 회복 3. 도망치기");
				int sel = Main.selectNumber("");

				if (sel == 1) {
					hero.attack(thanos);
					if (thanos.getHp() == 0) {
						System.out.printf("%s을(를) 죽여 승리했습니다.", thanos.getName());

						int count = thanos.getCount();
						System.out.printf("포션 \"%d개\"를 획득했습니다.\n", count);
						hero.setCount(hero.getCount() + count);

						this.boss = true;
						this.count[0]++;
						return;
					}

					thanos.attack(hero);
					if (hero.getHp() == 0) {
						System.out.printf("%s이(가) 사망하여 패배했습니다.", hero.getName());
						this.death = true;
						break;
					}
				} else if (sel == 2) {
					hero.recovery();
				} else if (sel == 3) {
					System.out.println("도망쳐라!");
					setPlayerPosition(before[0], before[1]);
					return;
				}
			}
		} else {
			System.out.println("보스방을 제외한 모든 방을 클리어시 입장가능합니다.");
			System.out.printf("남은 방 개수 : %d\n", 7 - this.map.getCount());
			this.map.setFieldDir(before[0], before[1], Map.HERO);
			setPlayerPosition(before[0], before[1]);
		}
	}

	// 빈라덴 던전
	private void binladenDungeon() {
		if (this.count[0] < 2) {
			System.err.println("Easy던전 2회 클리어 후 입장가능합니다.");
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
			party.add(new Terrorist("테러범" + (i + 1), 400, 30, count));
		}

		System.err.printf("\n%s 단체가 나타났다!!!\n", terrorist.getName());

		while (true) {
			System.out.printf("[ %s와의 전쟁 ]\n", terrorist.getName());
			System.out.println("1. 공격 2. 회복 3. 도망치기");
			int choice = Main.selectNumber("");

			if (choice == 1) {
				hero.attack(party.get(rNum - 1));
				if (party.get(rNum - 1).getHp() == 0) {
					System.out.printf("%s을(를) 죽였습니다.\n", party.get(rNum - 1).getName());
					getPortionByInferior(party, rNum);

					rNum--;
					if (rNum == 0) {
						System.out.printf("%s와의 전쟁에서 승리했습니다.\n", terrorist.getName());
						movedPlayer(before, after);
						setPlayerPosition(after[0], after[1]);

						this.map.setCount(this.map.getCount() + 1);
						break;
					}
				}

				party.get(rNum - 1).attack(hero);
				if (hero.getHp() == 0) {
					System.out.printf("%s이(가) 사망하여 패배했습니다.", hero.getName());
					this.death = true;
					return;
				}
			} else if (choice == 2) {
				hero.recovery();
			} else if (choice == 3) {
				System.out.println("도망쳐라!");
				setPlayerPosition(before[0], before[1]);
				break;
			}
		}
	}

	private void fightBinLaden(int[] before) {
		if (this.map.getCount() == 14) {
			System.out.printf("%s을 사살하라!\n", binladen.getName());

			while (true) {
				System.out.printf("[ Neptune Spear ]\n", binladen.getName());
				System.out.println("1. 공격 2. 회복 3. 도망치기");
				int sel = Main.selectNumber("");

				if (sel == 1) {
					if (binladen.getHide() == 0) {
						hero.attack(binladen);
						if (binladen.getHp() == 0) {
							System.out.printf("%s을(를) 죽여 승리했습니다.", binladen.getName());

							// 포션 획득 현장
							int count = binladen.getCount();
							System.out.printf("포션 \"%d개\"를 획득했습니다.\n", count);
							hero.setCount(hero.getCount() + count);

							this.boss = true;
							this.count[1]++;
							hero.setDotDamageCount(0);
							return;
						}
					}

					binladen.attack(hero);
					if (hero.getHp() == 0) {
						System.out.printf("%s이(가) 사망하여 패배했습니다.", hero.getName());
						this.death = true;
						break;
					}
				} else if (sel == 2) {
					hero.recovery();
				} else if (sel == 3) {
					System.out.println("도망쳐라!");
					setPlayerPosition(before[0], before[1]);
					return;
				}
			}
		} else {
			System.out.println("보스방을 제외한 모든 방을 클리어시 입장가능합니다.");
			System.out.printf("남은 방 개수 : %d\n", 14 - this.map.getCount());
			this.map.setFieldDir(before[0], before[1], Map.HERO);
			setPlayerPosition(before[0], before[1]);
		}
	}

	// 헬라 던전
	private void helaDungeon() {
		if (this.count[1] < 3) {
			System.err.println("Normal던전 3회 클리어 후 입장가능합니다.");
			return;
		}

		this.map = new Map(Map.MAP_SIZE_HARD);
		this.map.createMap(Map.HELA, Map.CHITAURI, Map.TERRORIST, Map.FENRIS);

		System.out.println("여기 오는 지 확인좀");

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
			party.add(new Fenris("펜리스" + (i + 1), 600, 50));
		}

		System.err.printf("\n%s가 나타났다!!!\n", fenris.getName());

		while (true) {
			System.out.printf("[ %s와의 난투 ]\n", fenris.getName());
			System.out.println("1. 공격 2. 회복 3. 도망치기");
			int choice = Main.selectNumber("");

			if (choice == 1) {
				hero.attack(party.get(num - 1));
				if (party.get(num - 1).getHp() == 0) {
					System.out.printf("%s을(를) 죽였습니다.\n", party.get(num - 1).getName());
					getPortionByInferior(party, num);

					num--;
					if (num == 0) {
						System.out.printf("%s와의 난투에서 승리했습니다.\n", fenris.getName());
						movedPlayer(before, after);
						setPlayerPosition(after[0], after[1]);

						this.map.setCount(this.map.getCount() + 1);
						break;
					}
				}

				party.get(num - 1).attack(hero);
				if (hero.getHp() == 0) {
					System.out.printf("%s이(가) 사망하여 패배했습니다.", hero.getName());
					this.death = true;
					return;
				}
			} else if (choice == 2) {
				hero.recovery();
			} else if (choice == 3) {
				System.out.println("도망쳐라!");
				setPlayerPosition(before[0], before[1]);
				break;
			}
		}
	}

	private void fightHela(int[] before) {
		if (this.map.getCount() == 23) {
			System.out.printf("%s을 물리쳐라!\n", hela.getName());

			while (true) {
				System.out.println("[ Defend Asgard ]");
				System.out.println("1. 공격 2. 회복 3. 도망치기");
				int sel = Main.selectNumber("");

				if (sel == 1) {
					hero.attack(hela);
					if (hela.getHp() == 0) {
						System.out.printf("%s을(를) 죽여 승리했습니다.", hela.getName());

						this.boss = true;
						this.count[2]++;
						hero.setDotDamageCount(0);
						return;
					}

					hela.attack(hero);
					if (hero.getHp() == 0) {
						System.out.printf("%s이(가) 사망하여 패배했습니다.", hero.getName());
						this.death = true;
						break;
					}
				} else if (sel == 2) {
					hero.recovery();
				} else if (sel == 3) {
					System.out.println("도망쳐라!");
					setPlayerPosition(before[0], before[1]);
					return;
				}
			}
		} else {
			System.out.println("보스방을 제외한 모든 방을 클리어시 입장가능합니다.");
			System.out.printf("남은 방 개수 : %d\n", 23 - this.map.getCount());
			this.map.setFieldDir(before[0], before[1], Map.HERO);
			setPlayerPosition(before[0], before[1]);
		}

	}

	private void dungeonRun() {
		int sel = selectDungeon("던전 선택");

		if (sel == 0)
			System.out.println("초기화면으로 돌아갑니다.");

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
			System.out.println("[ 던전 부시기 ]");
			System.out.println("1. 던전\n0. 게임종료");
			int select = Main.selectNumber("메뉴");

			if (select == 0) {
				System.out.println("게임을 종료합니다.");
				return;
			}

			if (select == 1) {
				dungeonRun();
			}

			if (clearCondition()) {
				System.out.println("Hard던전 클리어 완료 게임을 종료합니다.");
				break;
			}
		}
	}

}
