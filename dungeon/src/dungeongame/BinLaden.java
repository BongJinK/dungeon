package dungeongame;

public class BinLaden extends Villain {

	private final int MAX_HIDE = 3;
	private int hide;

	public BinLaden(String name, int hp, int att, int count) {
		super(name, hp, att, count);
		this.hide = MAX_HIDE;
	}

	public int getHide() {
		return hide;
	}

	@Override
	public void attack(Unit hero) {
		if (hero instanceof Hero) {
			Hero player = (Hero) hero;
			int damage = 0;
			if (this.hide != 0) {
				System.out.printf("%s�� ã�� ���߽��ϴ�.", this.getName());
				System.out.println("������ �Ұ����մϴ�.");
				System.out.printf("%s�� %s���� ����� �׷��� �������׽��ϴ�.\n", this.getName(), player.getName());
				damage = MAX_HIDE * Main.random(this.getAtt(), 80);
				this.setPower(damage);
				this.hide--;
				if (this.hide == 0) {
					System.out.printf("%s�� ��ġ�� �߰� �Ǿ����ϴ�.", this.getName());
					System.out.println("�� �̻� ���� ȸ�ǰ� �Ұ��� �մϴ�.");
				}
			} else {
				System.out.printf("%s�� %s���� �׷� ������ ���մϴ�.\n", this.getName(), player.getName());
				damage = Main.random(this.getAtt(), 1);
				this.setPower(damage);
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
		}
	}

}
