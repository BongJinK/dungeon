package dungeongame;

public class Villain extends Unit {

	private int power;
	private int count;
	
	// ����ġ ���� ���� ���� �� 
	// gold : ���� ����
//	int exp;
//	int gold;

	public Villain(String name) {
		super(name);
	}

	public Villain(String name, int hp, int att) {
		super(name, hp, att);
	}

	public Villain(String name, int hp, int att, int count) {
		super(name, hp, att);
		this.count = count;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public void attack(Unit hero) {
//		this.power = Main.random(this.getAtt(), 1);
//		int regen = Main.random(this.getAtt(), 1);
//
//		hero.setHp(hero.getHp() - this.power);
//		if (hero.getHp() <= 0)
//			hero.setHp(0);
//
//		this.setHp(this.getHp() + regen);
//
//		System.out.printf("%s�� %s��(��) �����մϴ�.\n", this.getName(), hero.getName());
//		Main.delay();
//		System.out.printf("%s�� HP�� %d��ŭ �����մϴ�.\n", hero.getName(), this.power);
//		System.out.printf("%s�� ���� HP : %d\n", hero.getName(), hero.getHp());
//		System.out.printf("���� HP : %d\n", this.getHp());
	}
}
