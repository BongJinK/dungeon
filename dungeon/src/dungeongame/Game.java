package dungeongame;


public class Game {

	private MapBattle battle;

	public Game() {
		this.battle = new MapBattle();
	}

	private void printMenu() {
		Main.sb.append("[ ������ Ű��� ]\n");
		Main.sb.append("1. ����\n0. ��������\n");
		Main.write(Main.sb.toString());
	}

	private int selectDungeon(String message) {
		System.out.println("1. Easy[Thanos]\n2. Normal[Bin Laden]");
		System.out.println("3. Hard[Hela]\n0. �ڷΰ���");

		int sel = Main.selectNumber(message);
		return sel;
	}

	private void clearMessage(String bossname, int number) {
		if (battle.isBoss()) {
			System.out.printf("%s���� %dȸ Ŭ���� �Ϸ�\n", bossname, battle.getClearCount()[number]);
			battle.setBoss(false);
		}
	}

	private void dungeonRun() {
		int sel = selectDungeon("���� ����");

		if (sel == 0)
			System.out.println("�ʱ�ȭ������ ���ư��ϴ�.");

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
			int select = Main.selectNumber("�޴�");

			if (select == 0) {
				System.out.println("������ �����մϴ�.");
				return;
			}

			if (select == 1) {
				dungeonRun();
			}
		}
	}

}
