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
			System.out.printf("%s�� %s���� �ΰ� ������ ���߽��׽��ϴ�!!\n", this.getName(), hero.getName());
		else {
			critical = 1;
			System.out.printf("%s�� %s���� �ָ����� �����մϴ�.\n", this.getName(), hero.getName());
		}
		int att = critical * Main.random(this.getAtt(), 70);
		this.setPower(att);
		hero.setHp(hero.getHp() - this.getPower());
		if (hero.getHp() <= 0)
			hero.setHp(0);

		Main.delay();
		System.out.printf("%s�� HP�� %d��ŭ �����մϴ�.\n", hero.getName(), this.getPower());
		System.out.printf("%s�� ���� HP : %d\n", hero.getName(), hero.getHp());
	}
}
