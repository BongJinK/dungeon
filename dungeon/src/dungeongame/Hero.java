package dungeongame;

public class Hero extends Unit implements Move {

	private int power;
	private int count;
	private int dotDamageCount;

	public Hero(String name, int hp, int att, int count) {
		super(name, hp, att);
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public int getPower() {
		return power;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getDotDamageCount() {
		return dotDamageCount;
	}

	public void setDotDamageCount(int dotDamageCount) {
		this.dotDamageCount = dotDamageCount;
	}

	@Override
	public void attack(Unit enemy) {
		if (enemy instanceof Thanos) {
			Thanos boss = (Thanos) enemy;
			bossThanos(boss);
		} else if (enemy instanceof BinLaden) {
			BinLaden boss = (BinLaden) enemy;
			bossBinLaden(boss);
		} else {
			this.power = Main.random.nextInt(this.getAtt()) + 10;

			enemy.setHp(enemy.getHp() - this.power);
			if (enemy.getHp() <= 0)
				enemy.setHp(0);

			System.out.printf("%s가 %s을(를) 공격합니다.\n", this.getName(), enemy.getName());
			Main.delay();
			System.out.printf("%s의 HP가 %d만큼 감소합니다.\n", enemy.getName(), this.power);
			System.out.printf("%s의 현재 HP : %d\n", enemy.getName(), enemy.getHp());
		}
	}

	private void bossThanos(Thanos boss) {
		this.power = Main.random.nextInt(this.getAtt()) + 10;

		if (boss.getShield() > 0) {
			int damage = boss.getShield() - this.power;

			if (damage < 0) {
				boss.setShield(0);
				boss.setHp(boss.getHp() + damage); // damage는 음수상태
			} else {
				boss.setShield(damage);
				System.out.printf("%s의 보호막이 %d만큼 감소합니다.\n", boss.getName(), this.power);
				System.out.printf("%s의 현재 보호막 : %d\n", boss.getName(), boss.getShield());
			}
		} else {
			boss.setHp(boss.getHp() - this.power);
			System.out.printf("%s의 HP가 %d만큼 감소합니다.\n", boss.getName(), this.power);
		}

		if (boss.getHp() <= 0) {
			boss.setHp(0);
		}

		System.out.printf("%s가 %s을(를) 공격합니다.\n", this.getName(), boss.getName());
		Main.delay();
		System.out.printf("%s의 현재 HP : %d\n", boss.getName(), boss.getHp());
	}

	private void bossBinLaden(BinLaden boss) {
		this.power = Main.random.nextInt(this.getAtt()) + 10;

		if (boss.getHide() > 0) {
			System.out.printf("%s를 찾는 중입니다....\n", boss.getName());
			System.err.println("공격이 불가능합니다.");
		} else {
			boss.setHp(boss.getHp() - this.power);
			if (boss.getHp() <= 0) {
				boss.setHp(0);
			}
			System.out.printf("%s가 %s을(를) 공격합니다.\n", this.getName(), boss.getName());
			Main.delay();
			System.out.printf("%s의 HP가 %d만큼 감소합니다.\n", boss.getName(), this.power);
			System.out.printf("%s의 현재 HP : %d\n", boss.getName(), boss.getHp());
		}
	}

	public void recovery() {
		if (this.count < 1)
			System.err.println("포션을 모두 소모하였습니다.");
		else {
			if (this.dotDamageCount != 0) {
				System.out.println("출혈을 회복합니다.");
				this.setDotDamageCount(0);
			}
			int recovery = Main.random(50, 30);
			System.out.printf("HP를 %d만큼 회복합니다.\n", recovery);
			super.setHp(super.getHp() + recovery);
		}
	}

	@Override
	public void move(int size) {
		while (true) {
			char move = Main.selectString("\tw(↑)\n   a(←)s(↓)d(→)");

			int y = this.getPosY();
			int x = this.getPosX();

			if (move == 'w')
				y--;
			else if (move == 's')
				y++;
			else if (move == 'a')
				x--;
			else if (move == 'd')
				x++;

			if (y < 0 || y >= size || x < 0 || x >= size)
				continue;

			this.setPosY(y);
			this.setPosX(x);
			break;
		}
	}

}
