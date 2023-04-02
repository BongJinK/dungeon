package dungeongame;


public class Game {

	private MapBattle battle;

	public Game() {
		this.battle = new MapBattle();
	}

	private void printMenu() {
		Main.sb.append("[ 봉진이 키우기 ]\n");
		Main.sb.append("1. 던전\n0. 게임종료\n");
		Main.write(Main.sb.toString());
	}

	private int selectDungeon(String message) {
		System.out.println("1. Easy[Thanos]\n2. Normal[Bin Laden]");
		System.out.println("3. Hard[Hela]\n0. 뒤로가기");

		int sel = Main.selectNumber(message);
		return sel;
	}

	private void clearMessage(String bossname, int number) {
		if (battle.isBoss()) {
			System.out.printf("%s던전 %d회 클리어 완료\n", bossname, battle.getClearCount()[number]);
			battle.setBoss(false);
		}
	}

	private void dungeonRun() {
		int sel = selectDungeon("던전 선택");

		if (sel == 0)
			System.out.println("초기화면으로 돌아갑니다.");

		if (sel == 1) {
			battle.thanosDungeon();
			clearMessage("Thanos", 0);

		} else if (sel == 2) {
			battle.binladenDungeon();
			clearMessage("Bin Laden", 1);

		} else if (sel == 3) {
			battle.helaDungeon();
			clearMessage("Hela", 2);
		}

		if (battle.isDeath()) {
			battle.getHero().setHp(battle.getHero().getMAXHP());
		}
	}

	public void run() {
		Main.sb.setLength(0);
		while (true) {
			printMenu();
			int select = Main.selectNumber("메뉴");

			if (select == 0) {
				System.out.println("게임을 종료합니다.");
				return;
			}

			if (select == 1) {
				dungeonRun();
			}
		}
	}

}
