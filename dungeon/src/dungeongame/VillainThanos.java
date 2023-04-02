package dungeongame;

public class VillainThanos extends Villain {

	private int shield;

	public VillainThanos(String name, int hp, int att, int count) {
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
		Main.sb.setLength(0);
		if (critical == 2)
			Main.sb.append(String.format("%s가 핑거 스냅을 사용합니다!!\n", this.getName()));
		else {
			critical = 1;
			Main.sb.append(String.format("%s가 %s에게 주먹질을 선사합니다.\n", this.getName(), hero.getName()));
		}
		int att = critical * Main.random(this.getAtt(), 70);
		this.setPower(att);
		hero.setHp(hero.getHp() - this.getPower());
		if (hero.getHp() <= 0)
			hero.setHp(0);

		Main.delay();
		Main.sb.append(String.format("%s의 HP가 %d만큼 감소합니다.\n", hero.getName(), this.getPower()));
		Main.sb.append(String.format("%s의 현재 HP : %d\n", hero.getName(), hero.getHp()));
		Main.write(Main.sb.toString());
	}
}
