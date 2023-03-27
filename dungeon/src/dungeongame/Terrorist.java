package dungeongame;

public class Terrorist extends Villain {

	private final int DOT_MAX = 3;
	private final int DOT_DAMAGE = 2;

	public Terrorist(String name) {
		super(name);
	}

	public Terrorist(String name, int hp, int att, int count) {
		super(name, hp, att, count);
	}

	public int getDOT_MAX() {
		return DOT_MAX;
	}

	@Override
	public void attack(Unit hero) {
		this.setPower(Main.random(this.getAtt(), 15));

		if (hero instanceof Hero) {
			Hero player = (Hero) hero;
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

			System.out.printf("%s�� %s��(��) �����մϴ�.\n", this.getName(), player.getName());
			System.out.printf("%s�� �������� �������ظ� ���������� �Խ��ϴ�.\n", this.getName());
			System.out.printf("���� ���� : %d(�ִ� %d����)\n", dotCount, DOT_MAX);
			Main.delay();
			System.out.printf("%s�� HP�� %d��ŭ �����մϴ�.\n", player.getName(), damage);
			System.out.printf("%s�� ���� HP : %d\n", player.getName(), player.getHp());
		}
	}

}
