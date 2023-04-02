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
				Main.sb.append(String.format("%s가 흥분하여 거대해집니다.\n", fenris.getName()));
			} else if (fenris.getHp() <= 0 && fenris.getSingleUse() == 0) {
				fenris.setHp(0);
			}

			Main.sb.append(String.format("%s가 %s을(를) 공격합니다.\n", this.getName(), enemy.getName()));
			Main.delay();
			Main.sb.append(String.format("%s의 HP가 %d만큼 감소합니다.\n", enemy.getName(), this.power));
			Main.sb.append(String.format("%s의 현재 HP : %d\n", enemy.getName(), enemy.getHp()));
		} else {
			this.power = Main.random.nextInt(this.getAtt()) + 10;

			enemy.setHp(enemy.getHp() - this.power);
			if (enemy.getHp() <= 0)
				enemy.setHp(0);

			Main.sb.append(String.format("%s가 %s을(를) 공격합니다.\n", this.getName(), enemy.getName()));
			Main.delay();
			Main.sb.append(String.format("%s의 HP가 %d만큼 감소합니다.\n", enemy.getName(), this.power));
			Main.sb.append(String.format("%s의 현재 HP : %d\n", enemy.getName(), enemy.getHp()));
		}
		Main.write(Main.sb.toString());
	}

	private void bossThanos(VillainThanos boss) {
		this.power = Main.random.nextInt(this.getAtt()) + 10;

		if (boss.getShield() > 0) {
			int damage = boss.getShield() - this.power;

			if (damage < 0) {
				boss.setShield(0);
				boss.setHp(boss.getHp() + damage); // damage는 음수상태
			} else {
				boss.setShield(damage);
				Main.sb.append(String.format("%s의 보호막이 %d만큼 감소합니다.\n", boss.getName(), this.power));
				Main.sb.append(String.format("%s의 현재 보호막 : %d\n", boss.getName(), boss.getShield()));
			}
		} else {
			boss.setHp(boss.getHp() - this.power);
			Main.sb.append(String.format("%s의 HP가 %d만큼 감소합니다.\n", boss.getName(), this.power));
		}

		if (boss.getHp() <= 0) {
			boss.setHp(0);
		}

		Main.sb.append(String.format("%s가 %s을(를) 공격합니다.\n", this.getName(), boss.getName()));
		Main.delay();
		Main.sb.append(String.format("%s의 현재 HP : %d\n", boss.getName(), boss.getHp()));
	}

	private void bossBinLaden(VillainBinLaden boss) {
		this.power = Main.random.nextInt(this.getAtt()) + 10;

		if (boss.getHide() > 0) {
			Main.sb.append(String.format("%s를 찾는 중입니다....\n", boss.getName()));
			Main.sb.append("공격이 불가능합니다.\n");
		} else {
			boss.setHp(boss.getHp() - this.power);
			if (boss.getHp() <= 0) {
				boss.setHp(0);
			}
			Main.sb.append(String.format("%s가 %s을(를) 공격합니다.\n", this.getName(), boss.getName()));
			Main.delay();
			Main.sb.append(String.format("%s의 HP가 %d만큼 감소합니다.\n", boss.getName(), this.power));
			Main.sb.append(String.format("%s의 현재 HP : %d\n", boss.getName(), boss.getHp()));
		}
	}

	private void bossHela(VillainHela boss) {
		this.power = Main.random.nextInt(this.getAtt()) + 10;

		boss.setHp(boss.getHp() - this.power);
		if (boss.getHp() <= 0) {
			boss.setHp(0);
		}
		Main.sb.append(String.format("%s가 %s을(를) 공격합니다.\n", this.getName(), boss.getName()));
		Main.sb.append(String.format("%s의 HP가 %d만큼 감소합니다.\n", boss.getName(), this.power));
		Main.sb.append(String.format("%s의 현재 HP : %d\n", boss.getName(), boss.getHp()));
		Main.delay();
	}

	public void recovery() {
		Main.sb.setLength(0);
		int rNum = Main.random(100, 1);

		if (this.count < 1)
			Main.sb.append("포션을 모두 소모하였습니다.");
		else {
			if (this.dotDamageCount != 0) {
				Main.sb.append("출혈을 회복합니다.");
				this.setDotDamageCount(0);
			}
			int recovery = 0;
			if (rNum != 77)
				recovery = Main.random(50, 30);
			else {
				Main.sb.append("초거대 물약을 복용합니다.");
				Main.delay();
				recovery = 1000;
			}
			Main.sb.append(String.format("HP를 %d만큼 회복합니다.\n", recovery));
			super.setHp(super.getHp() + recovery);
		}
		Main.write(Main.sb.toString());
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
