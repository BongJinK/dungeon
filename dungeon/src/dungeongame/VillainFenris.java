package dungeongame;

public class VillainFenris extends Villain {

	private final int GIGANTIC = 400;
	private int singleUse;

	public VillainFenris(String name) {
		super(name);
	}

	public VillainFenris(String name, int hp, int att) {
		super(name, hp, att);
		this.setCount(3);
		this.singleUse = 1;
	}

	public int getGIGANTIC() {
		return GIGANTIC;
	}

	public int getSingleUse() {
		return this.singleUse;
	}

	public void setSingleUse(int singleUse) {
		this.singleUse = singleUse;
	}

	@Override
	public void attack(Unit hero) {
		if (hero instanceof UnitHero) {
			UnitHero player = (UnitHero) hero;
			Main.sb.setLength(0);

			this.setPower(Main.random(this.getAtt(), 20));
			int damage = this.getPower();
			if (this.singleUse == 0) {
				Main.sb.append(String.format("%s�� ����Ͽ� ���ݷ��� ���� ����մϴ�.\n", this.getName()));
				damage += 10;
			}
			if (player.getDotDamageCount() == 0) {
				player.setHp(player.getHp() - damage);
			} else {
				damage += player.getDotDamageCount() * 2;
				Main.sb.append("�������ظ� ���������� �Խ��ϴ�.\n");
				Main.sb.append(String.format("���� ���� : %d (�ִ� 3����)\n", player.getDotDamageCount()));
				player.setHp(player.getHp() - damage);
			}

			if (player.getHp() <= 0) {
				player.setHp(0);
			}

			Main.sb.append(String.format("%s�� %s��(��) �����մϴ�.\n", this.getName(), hero.getName()));
			Main.delay();
			Main.sb.append(String.format("%s�� HP�� %d��ŭ �����մϴ�.\n", hero.getName(), damage));
			Main.sb.append(String.format("%s�� ���� HP : %d\n", hero.getName(), hero.getHp()));
			Main.write(Main.sb.toString());
		}
	}

}
