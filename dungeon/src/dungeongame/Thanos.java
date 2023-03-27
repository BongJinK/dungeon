package dungeongame;

public class Thanos extends Villain {

	private int shield;

	public Thanos(String name, int hp, int att, int count) {
		super(name, hp, att, count);
		this.shield = 200;
	}

	public int getShield() {
		return shield;
	}

	public void setShield(int shield) {
		this.shield = shield;
	}

	@Override
	public void attack(Unit hero) {
		int critical = Main.random.nextInt(5);

		if (critical == 2)
			System.out.printf("%s가 %s에게 핑거 스냅을 적중시켰습니다!!\n", this.getName(), hero.getName());
		else {
			critical = 1;
			System.out.printf("%s가 %s에게 주먹질을 선사합니다.\n", this.getName(), hero.getName());
		}
		int att = critical * Main.random(this.getAtt(), 70);
		this.setPower(att);
		hero.setHp(hero.getHp() - this.getPower());
		if (hero.getHp() <= 0)
			hero.setHp(0);

		Main.delay();
		System.out.printf("%s의 HP가 %d만큼 감소합니다.\n", hero.getName(), this.getPower());
		System.out.printf("%s의 현재 HP : %d\n", hero.getName(), hero.getHp());
	}
}
