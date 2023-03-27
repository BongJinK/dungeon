package dungeongame;

public class Fenris extends Villain {

	private final int GIGANTIC = 400;
	private int singleUse;

	public Fenris(String name) {
		super(name);
	}

	public Fenris(String name, int hp, int att) {
		super(name, hp, att);
		this.setCount(3);
		this.singleUse = 1;
	}

	@Override
	public void attack(Unit hero) {
		if (hero instanceof Hero) {
			Hero player = (Hero) hero;

			if (this.getHp() <= GIGANTIC / 2 && this.singleUse == 1) {
				this.setHp(this.getHp() + GIGANTIC);
				this.singleUse--;
				System.out.printf("%s�� ����Ͽ� �Ŵ������ϴ�.\n", this.getName());
				System.out.println("HP�� ȸ���ϰ� ���ݷ��� ���� ����մϴ�.");
			}

			this.setPower(Main.random(this.getAtt(), 20));
			int damage = this.getPower();
			if (this.singleUse != 1) {
				damage += 10;
			}
			if (player.getDotDamageCount() == 0) {
				player.setHp(player.getHp() - damage);
			} else {
				damage += player.getDotDamageCount() * 2;
				System.out.println("�������ظ� ���������� �Խ��ϴ�.");
				System.out.printf("���� ���� : %d", player.getDotDamageCount());
				System.out.println("(�ִ� 3����)");
				player.setHp(player.getHp() - damage);
			}

			if (player.getHp() <= 0) {
				player.setHp(0);
			}
			System.out.printf("%s�� %s��(��) �����մϴ�.", this.getName(), hero.getName());
			Main.delay();
			System.out.printf("%s�� HP�� %d��ŭ �����մϴ�.\n", hero.getName(), damage);
			System.out.printf("%s�� ���� HP : %d\n", hero.getName(), hero.getHp());
		}
	}

}
