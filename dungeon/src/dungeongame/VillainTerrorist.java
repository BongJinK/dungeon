package dungeongame;

public class VillainTerrorist extends Villain {

	private final int DOT_MAX = 3;
	private final int DOT_DAMAGE = 2;

	public VillainTerrorist(String name) {
		super(name);
	}

	public VillainTerrorist(String name, int hp, int att, int count) {
		super(name, hp, att, count);
	}

	public int getDOT_MAX() {
		return DOT_MAX;
	}

	@Override
	public void attack(Unit hero) {
		this.setPower(Main.random(this.getAtt(), 15));

		if (hero instanceof UnitHero) {
			UnitHero player = (UnitHero) hero;
			Main.sb.setLength(0);

			int dotCount = player.getDotDamageCount();
			if (dotCount < DOT_MAX) {
				player.setDotDamageCount(dotCount + 1);
				dotCount++;
			}
			int damage = this.getPower() + dotCount * DOT_DAMAGE;
			System.out.println(dotCount * DOT_DAMAGE);
			player.setHp(player.getHp() - damage);

			if (player.getHp() <= 0)
				player.setHp(0);

			Main.sb.append(String.format("%s�� %s��(��) �����մϴ�.\n", this.getName(), player.getName()));
			Main.sb.append(String.format("%s�� �������� �������ظ� ���������� �Խ��ϴ�.\n", this.getName()));
			Main.sb.append(String.format("���� ���� : %d(�ִ� %d����)\n", dotCount, DOT_MAX));
			Main.delay();
			Main.sb.append(String.format("%s�� HP�� %d��ŭ �����մϴ�.\n", player.getName(), damage));
			Main.sb.append(String.format("%s�� ���� HP : %d\n", player.getName(), player.getHp()));
			Main.write(Main.sb.toString());
		}
	}

}
