package dungeongame;

public class Villain extends Unit {

	private int power;
	private int count;
	
	// 경험치 각각 몬스터 고정 값 
	// gold : 범위 랜덤
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
//		System.out.printf("%s가 %s을(를) 공격합니다.\n", this.getName(), hero.getName());
//		Main.delay();
//		System.out.printf("%s의 HP가 %d만큼 감소합니다.\n", hero.getName(), this.power);
//		System.out.printf("%s의 현재 HP : %d\n", hero.getName(), hero.getHp());
//		System.out.printf("좀비 HP : %d\n", this.getHp());
	}
}
