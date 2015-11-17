import java.util.Random;

public class Particle {
	private Random random = new Random(System.currentTimeMillis()); // 产生随机数
	private int pBest; // 记录粒子自身历史最优值
	private int [] sequence; // 记录粒子选择的路径
	private int dim; // 城市数量（计算维度）
	private Particle pBestParticle;
	
	public Particle() {
		super();
		this.pBestParticle = null;
	}
	
	public void init(int num) {
		this.dim = num;
		this.pBest = Integer.MAX_VALUE;
		this.sequence = new int[num];
		this.sequence[0] = random.nextInt();
		this.sequence[0] = Math.abs(this.sequence[0]);
		this.sequence[0] = this.sequence[0] % 48;
		this.sequence[0] = this.sequence[0] == 0 ? 48 : this.sequence[0];
		
		for (int i = 1;i < num;i ++) {
			this.sequence[i] = this.sequence[i - 1] + 1;
			this.sequence[i] = this.sequence[i] % 48;
			this.sequence[i] = this.sequence[i] == 0 ? 48 : this.sequence[i];
		}
		this.pBestParticle = this;
	}
	
	public int getSequace(int i) {
		return this.sequence[i];
	}
	
	public int cmp(int gBest) {
		if(this.pBest > gBest) {
			this.pBest = gBest;
		}
		
		return this.pBest;
	}
	
	public int calFitness(int cityNum, int distance[][]) {
		int len = 0;
		// 粒子中的城市序列，起始城市,城市1,城市2...城市n
		for (int i = 1; i < cityNum; i++) {
			len += distance[sequence[i - 1]][sequence[i]];
		}
		// 城市n,起始城市
		len += distance[sequence[cityNum - 1]][sequence[0]];
		
		if(this.pBest < len) {
			this.pBestParticle = this;
		}
		
		this.pBest = len < this.pBest ? len : this.pBest;
	
		return this.pBest;
	}
	
	public void updateSpeed(double w, double c1, double c2, Particle tourBest) {
		// 速度更新函数
		/*vid = w∙vid+c1∙rand（）∙（pid-xid）+c2∙Rand（）∙（pgd-xid）  （1a）
	       xid = xid+vid                                       （1b）*/
		
		for(int i = 0;i < this.dim;i ++) {
			double tmp = w * this.sequence[i] + 
					c1 * this.random.nextDouble() * (tourBest.getSequace(i) - this.sequence[i]) + 
					c2 * this.random.nextDouble() * (this.pBestParticle.getSequace(i) - this.sequence[i]);
			this.sequence[i] += (int)tmp;
			this.sequence[i] = Math.abs(this.sequence[i]);
			this.sequence[i] %= 48;
			this.sequence[i] = this.sequence[i] == 0 ? 48 : this.sequence[i];
		}
	}
}
