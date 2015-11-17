import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class PSO implements TSPSlove{
	private int scale;// 粒子数目
	private int cityNum; // 城市数量，染色体长度
	private int MAX_PAR; // 运行代数
	private int [][] distance; // 距离矩阵
	private int gBest; // 全局最优适应值
	private Particle bestTour;
	private Particle []particles;
	
	/**
	 * @param s
	 *            种群规模
	 * @param c
	 *            最大城市数
	 * @param m
	 *            最大迭代次数
	 * 
	 **/
	public PSO(int s, int c, int m) {
		this.scale = s;
		this.cityNum = c;
		this.MAX_PAR = m;
	}
	
	@Override
	public void init(String filename) throws IOException {
		// TODO Auto-generated method stub
		// 读取数据
		int[] x;
		int[] y;
		String strbuff;
		BufferedReader data = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		distance = new int[cityNum + 1][cityNum + 1];
		x = new int[cityNum];
		y = new int[cityNum];
		for (int i = 0; i < cityNum; i++) {
			// 读取一行数据，数据格式1 6734 1453
			strbuff = data.readLine();
			// 字符分割
			String[] strcol = strbuff.split(" ");
			x[i] = Integer.valueOf(strcol[1]);// x坐标
			y[i] = Integer.valueOf(strcol[2]);// y坐标
		}

		// 计算距离矩阵
		// 针对具体问题，距离计算方法也不一样，此处用的是att48作为案例，它有48个城市，距离计算方法为伪欧氏距离，最优值为10628
		for (int i = 1; i < cityNum ; i++) {
			distance[i][i] = 0; // 对角线为0
			for (int j = i + 1; j < cityNum; j++) {
				double rij = Math.sqrt(((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j]) * (y[i] - y[j])) / 10.0);
				// 四舍五入，取整
				int tij = (int) Math.round(rij);
				if (tij < rij) {
					distance[i][j] = tij + 1;
					distance[j][i] = distance[i][j];
				} else {
					distance[i][j] = tij;
					distance[j][i] = distance[i][j];
				}
			}
		}
		distance[cityNum - 1][cityNum - 1] = 0;
		
		this.gBest = Integer.MAX_VALUE;
		this.bestTour = null;
		this.particles = new Particle[this.scale];
		
		for(int i = 0;i < this.scale;i ++) {
			this.particles[i] = new Particle();
			this.particles[i].init(cityNum);
		}
	}

	@Override
	public void slove() {
		// TODO Auto-generated method stub
		// 开始迭代
		for(int time = 1; time < this.MAX_PAR; time ++) {
		// 完成适应值计算并更新自身与全局最佳适应值
			for (int i = 0; i < this.scale; i++) {
				int tmp = this.particles[i].calFitness(this.cityNum, this.distance);
				if (tmp < this.gBest) {
					this.bestTour = this.particles[i];
					this.gBest = tmp;
				}
			}
			
			for (int i = 0; i < this.scale; i++) {				
				this.particles[i].updateSpeed(0.85,2,2,this.bestTour);
			}
		}
		System.out.println("最佳长度");
		System.out.println(this.gBest);
		System.out.println("最佳路径：");
		for (int i = 0;i < this.cityNum;i ++) {
			System.out.print(this.bestTour.getSequace(i) + ",");
		}
		System.out.println("\nend");
	}
}
