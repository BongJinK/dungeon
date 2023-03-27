package dungeongame;

public class Hela extends Villain {

	private final int EVEN = 2;
	private int healingfactor;
	private String saying;

	public Hela(String name, int hp, int att) {
		super(name, hp, att);
		healingfactor = 10;
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
		int regen = Main.random(this.getAtt(), 1) * healingfactor;

		if (hero instanceof Hero) {
			Hero player = (Hero) hero;

			int damage = this.getPower();
			if (Main.random(healingfactor, 0) % EVEN == 0) {
				System.out.println(saying);
				System.out.printf("%s�� ���ں��� ������ ���մϴ�.\n", this.getName());
				this.setPower(this.getPower() * EVEN);
				damage *= EVEN;
				this.setHp(this.getHp() + regen * EVEN);
			} else {
				System.out.printf("%s�� %s��(��) �����մϴ�.\n", this.getName(), player.getName());
				this.setHp(this.getHp() + regen);
			}

			if (player.getDotDamageCount() != 0) {
				System.out.println("�������ظ� ���������� �Խ��ϴ�.");
				System.out.printf("���� ���� : %d", player.getDotDamageCount());
				System.out.println("(�ִ� 3����)");
				damage += player.getDotDamageCount() * 2;
			}

			player.setHp(player.getHp() - damage);
			if (player.getHp() <= 0)
				player.setHp(0);

			Main.delay();
			System.out.printf("%s�� HP�� %d��ŭ �����մϴ�.\n", player.getName(), damage);
			System.out.printf("%s�� ���� HP : %d\n", player.getName(), player.getHp());
			System.out.printf("%s HP : %d\n", this.getName(), this.getHp());
		}
	}

}
