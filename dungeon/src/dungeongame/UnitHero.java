package dungeongame;

public class UnitHero extends Unit implements Move {

	private final int MAXHP;
	private int power;
	private int count;
	private int dotDamageCount;

	int level;

	public UnitHero(String name, int hp, int att, int count) {
		super(name, hp, att);
		MAXHP = hp;
		this.count = count;
	}

	public int getMAXHP() {
		return MAXHP;
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
		Main.sb.setLength(0);
		if (enemy instanceof VillainThanos) {
			VillainThanos boss = (VillainThanos) enemy;
			bossThanos(boss);
		} else if (enemy instanceof VillainBinLaden) {
			VillainBinLaden boss = (VillainBinLaden) enemy;
			bossBinLaden(boss);
		} else if (enemy instanceof VillainHela) {
			VillainHela boss = (VillainHela) enemy;
			bossHela(boss);
		} else if (enemy instanceof VillainFenris) {
			VillainFenris fenris = (VillainFenris) enemy;

			this.power = Main.random.nextInt(this.getAtt()) + 10;

			fenris.setHp(fenris.getHp() - this.power);
			if (fenris.getHp() <= 0 && fenris.getSingleUse() == 1) {
				fenris.setSingleUse(0);
				fenris.setHp(fenris.getGIGANTIC());
				Main.sb.append(String.format("%s�� ����Ͽ� �Ŵ������ϴ�.\n", fenris.getName()));
			} else if (fenris.getHp() <= 0 && fenris.getSingleUse() == 0) {
				fenris.setHp(0);
			}

			Main.sb.append(String.format("%s�� %s��(��) �����մϴ�.\n", this.getName(), enemy.getName()));
			Main.delay();
			Main.sb.append(String.format("%s�� HP�� %d��ŭ �����մϴ�.\n", enemy.getName(), this.power));
			Main.sb.append(String.format("%s�� ���� HP : %d\n", enemy.getName(), enemy.getHp()));
		} else {
			this.power = Main.random.nextInt(this.getAtt()) + 10;

			enemy.setHp(enemy.getHp() - this.power);
			if (enemy.getHp() <= 0)
				enemy.setHp(0);

			Main.sb.append(String.format("%s�� %s��(��) �����մϴ�.\n", this.getName(), enemy.getName()));
			Main.delay();
			Main.sb.append(String.format("%s�� HP�� %d��ŭ �����մϴ�.\n", enemy.getName(), this.power));
			Main.sb.append(String.format("%s�� ���� HP : %d\n", enemy.getName(), enemy.getHp()));
		}
		Main.write(Main.sb.toString());
	}

	private void bossThanos(VillainThanos boss) {
		this.power = Main.random.nextInt(this.getAtt()) + 10;

		if (boss.getShield() > 0) {
			int damage = boss.getShield() - this.power;

			if (damage < 0) {
				boss.setShield(0);
				boss.setHp(boss.getHp() + damage); // damage�� ��������
			} else {
				boss.setShield(damage);
				Main.sb.append(String.format("%s�� ��ȣ���� %d��ŭ �����մϴ�.\n", boss.getName(), this.power));
				Main.sb.append(String.format("%s�� ���� ��ȣ�� : %d\n", boss.getName(), boss.getShield()));
			}
		} else {
			boss.setHp(boss.getHp() - this.power);
			Main.sb.append(String.format("%s�� HP�� %d��ŭ �����մϴ�.\n", boss.getName(), this.power));
		}

		if (boss.getHp() <= 0) {
			boss.setHp(0);
		}

		Main.sb.append(String.format("%s�� %s��(��) �����մϴ�.\n", this.getName(), boss.getName()));
		Main.delay();
		Main.sb.append(String.format("%s�� ���� HP : %d\n", boss.getName(), boss.getHp()));
	}

	private void bossBinLaden(VillainBinLaden boss) {
		this.power = Main.random.nextInt(this.getAtt()) + 10;

		if (boss.getHide() > 0) {
			Main.sb.append(String.format("%s�� ã�� ���Դϴ�....\n", boss.getName()));
			Main.sb.append("������ �Ұ����մϴ�.\n");
		} else {
			boss.setHp(boss.getHp() - this.power);
			if (boss.getHp() <= 0) {
				boss.setHp(0);
			}
			Main.sb.append(String.format("%s�� %s��(��) �����մϴ�.\n", this.getName(), boss.getName()));
			Main.delay();
			Main.sb.append(String.format("%s�� HP�� %d��ŭ �����մϴ�.\n", boss.getName(), this.power));
			Main.sb.append(String.format("%s�� ���� HP : %d\n", boss.getName(), boss.getHp()));
		}
	}

	private void bossHela(VillainHela boss) {
		this.power = Main.random.nextInt(this.getAtt()) + 10;

		boss.setHp(boss.getHp() - this.power);
		if (boss.getHp() <= 0) {
			boss.setHp(0);
		}
		Main.sb.append(String.format("%s�� %s��(��) �����մϴ�.\n", this.getName(), boss.getName()));
		Main.sb.append(String.format("%s�� HP�� %d��ŭ �����մϴ�.\n", boss.getName(), this.power));
		Main.sb.append(String.format("%s�� ���� HP : %d\n", boss.getName(), boss.getHp()));
		Main.delay();
	}

	public void recovery() {
		Main.sb.setLength(0);
		int rNum = Main.random(100, 1);

		if (this.count < 1)
			Main.sb.append("������ ��� �Ҹ��Ͽ����ϴ�.");
		else {
			if (this.dotDamageCount != 0) {
				Main.sb.append("������ ȸ���մϴ�.");
				this.setDotDamageCount(0);
			}
			int recovery = 0;
			if (rNum != 77)
				recovery = Main.random(50, 30);
			else {
				Main.sb.append("�ʰŴ� ������ �����մϴ�.");
				Main.delay();
				recovery = 1000;
			}
			Main.sb.append(String.format("HP�� %d��ŭ ȸ���մϴ�.\n", recovery));
			super.setHp(super.getHp() + recovery);
		}
		Main.write(Main.sb.toString());
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
