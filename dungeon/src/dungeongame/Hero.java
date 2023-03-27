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

			System.out.printf("%s�� %s��(��) �����մϴ�.\n", this.getName(), enemy.getName());
			Main.delay();
			System.out.printf("%s�� HP�� %d��ŭ �����մϴ�.\n", enemy.getName(), this.power);
			System.out.printf("%s�� ���� HP : %d\n", enemy.getName(), enemy.getHp());
		}
	}

	private void bossThanos(Thanos boss) {
		this.power = Main.random.nextInt(this.getAtt()) + 10;

		if (boss.getShield() > 0) {
			int damage = boss.getShield() - this.power;

			if (damage < 0) {
				boss.setShield(0);
				boss.setHp(boss.getHp() + damage); // damage�� ��������
			} else {
				boss.setShield(damage);
				System.out.printf("%s�� ��ȣ���� %d��ŭ �����մϴ�.\n", boss.getName(), this.power);
				System.out.printf("%s�� ���� ��ȣ�� : %d\n", boss.getName(), boss.getShield());
			}
		} else {
			boss.setHp(boss.getHp() - this.power);
			System.out.printf("%s�� HP�� %d��ŭ �����մϴ�.\n", boss.getName(), this.power);
		}

		if (boss.getHp() <= 0) {
			boss.setHp(0);
		}

		System.out.printf("%s�� %s��(��) �����մϴ�.\n", this.getName(), boss.getName());
		Main.delay();
		System.out.printf("%s�� ���� HP : %d\n", boss.getName(), boss.getHp());
	}

	private void bossBinLaden(BinLaden boss) {
		this.power = Main.random.nextInt(this.getAtt()) + 10;

		if (boss.getHide() > 0) {
			System.out.printf("%s�� ã�� ���Դϴ�....\n", boss.getName());
			System.err.println("������ �Ұ����մϴ�.");
		} else {
			boss.setHp(boss.getHp() - this.power);
			if (boss.getHp() <= 0) {
				boss.setHp(0);
			}
			System.out.printf("%s�� %s��(��) �����մϴ�.\n", this.getName(), boss.getName());
			Main.delay();
			System.out.printf("%s�� HP�� %d��ŭ �����մϴ�.\n", boss.getName(), this.power);
			System.out.printf("%s�� ���� HP : %d\n", boss.getName(), boss.getHp());
		}
	}

	public void recovery() {
		if (this.count < 1)
			System.err.println("������ ��� �Ҹ��Ͽ����ϴ�.");
		else {
			if (this.dotDamageCount != 0) {
				System.out.println("������ ȸ���մϴ�.");
				this.setDotDamageCount(0);
			}
			int recovery = Main.random(50, 30);
			System.out.printf("HP�� %d��ŭ ȸ���մϴ�.\n", recovery);
			super.setHp(super.getHp() + recovery);
		}
	}

	@Override
	public void move(int size) {
		while (true) {
			char move = Main.selectString("\tw(��)\n   a(��)s(��)d(��)");

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
