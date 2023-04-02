package dungeongame;

public class VillainHela extends Villain {

	private final int EVEN = 2;
	private int healingfactor;
	private String saying;

	public VillainHela(String name, int hp, int att) {
		super(name, hp, att);
		healingfactor = 5;
		saying = "Kneel. Before your Queen";
	}

	public int getHealingfactor() {
		return healingfactor;
	}

	public String getDeath() {
		return saying;
	}

	public void attack(Unit hero) {
		this.setPower(Main.random(this.getAtt(), 110));
		int regen = Main.random(this.getAtt(), healingfactor * 10);

		if (hero instanceof UnitHero) {
			UnitHero player = (UnitHero) hero;
			Main.sb.setLength(0);

			int damage = this.getPower();
			if (Main.random(healingfactor, 0) % EVEN == 0) {
				Main.sb.append(saying + "\n");
				Main.sb.append(String.format("%s�� ���ں��� ������ ���մϴ�.\n", this.getName()));
				Main.sb.append(String.format("%s�� ü���� %d��ŭ ȸ���մϴ�.\n", this.getName(), regen));
				Main.sb.append(String.format("%s HP : %d\n", this.getName(), this.getHp()));
				this.setPower(this.getPower() * EVEN);
				damage *= EVEN;
				this.setHp(this.getHp() + regen);
			} else {
				Main.sb.append(String.format("%s�� %s��(��) �����մϴ�.\n", this.getName(), player.getName()));
			}

			if (player.getDotDamageCount() != 0) {
				Main.sb.append("�������ظ� ���������� �Խ��ϴ�.\n");
				Main.sb.append(String.format("���� ���� : %d (�ִ� 3����)\n", player.getDotDamageCount()));
				damage += player.getDotDamageCount() * 2;
			}

			player.setHp(player.getHp() - damage);
			if (player.getHp() <= 0)
				player.setHp(0);

			Main.delay();
			Main.sb.append(String.format("%s�� HP�� %d��ŭ �����մϴ�.\n", player.getName(), damage));
			Main.sb.append(String.format("%s�� ���� HP : %d\n", player.getName(), player.getHp()));
			Main.write(Main.sb.toString());
		}
	}

}
