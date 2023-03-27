package dungeongame;

public class Chitauri extends Villain {

	private int repeat;

	public Chitauri(String name) {
		super(name);
	}

	public Chitauri(String name, int hp, int att, int count) {
		super(name, hp, att, count);
	}

	@Override
	public void attack(Unit hero) {
		this.repeat = 2;
		this.setPower(Main.random(this.getAtt(), 10));

		if (hero instanceof Hero) {
			Hero player = (Hero) hero;

			System.out.printf("%s�� %s��(��) �����մϴ�.\n", this.getName(), player.getName());
			System.out.printf("%s�� �ѹ� �� �����մϴ�.\n", this.getName());
			int damage = this.getPower();
			for (int i = 0; i < this.repeat; i++) {
				if (player.getDotDamageCount() == 0)
					player.setHp(player.getHp() - damage);
				else {
					damage = this.getPower() + player.getDotDamageCount() * 2;
					System.out.println("�������ظ� ���������� �Խ��ϴ�.");
					System.out.printf("���� ���� : %d", player.getDotDamageCount());
					System.out.println("(�ִ� 3����)");
					player.setHp(player.getHp() - damage);
				}
			}
			if (player.getHp() <= 0)
				player.setHp(0);

			Main.delay();
			damage *= 2;
			System.out.printf("%s�� HP�� %d��ŭ �����մϴ�.\n", player.getName(), damage);
			System.out.printf("%s�� ���� HP : %d\n", player.getName(), player.getHp());
		}
	}

}
