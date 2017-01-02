public class PercolationStats {

	private double mean = 0.0;

	private double std = 0.0;

	private double upperConf = 0.0;

	private double lowerConf = 0.0;

	public PercolationStats(int n, int t) {
		if (n <= 0 || t <= 0)
			throw new java.lang.IllegalArgumentException();
		double[] res = new double[t];
		for (int m = 0; m < t; m++) {
			Percolation perObj = new Percolation(n);
			for (int c = 0; c < n * n; c++) {
				int pt = (int) (Math.random() * n * n);
				int i = pt / n + 1, j = pt % n + 1;
				while (perObj.isOpen(i, j)) {
					pt = (int) (Math.random() * n * n);
					i = pt / n + 1;
					j = pt % n + 1;
				}
				perObj.open(i, j);
				if (perObj.percolates()) {
					res[m] = ((double) c) / (n * n);
					break;
				}
			}
		}
		// Mean
		for (int m = 0; m < t; m++)
			mean += res[m];
		mean /= t;
		// Std
		for (int m = 0; m < t; m++)
			std += (res[m] - mean) * (res[m] - mean);
		std /= (t - 1);
		std = Math.sqrt(std);
		// confidence
		lowerConf = mean - 1.96 * std / Math.sqrt(t + 0.0);
		upperConf = mean + 1.96 * std / Math.sqrt(t + 0.0);
	}

	public double mean() {
		return mean;
	}

	public double stddev() {
		return std;
	}

	public double confidenceLo() {
		return lowerConf;
	}

	public double confidenceHi() {
		return upperConf;
	}

	public static void main(String[] args) {
		PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
				Integer.parseInt(args[1]));
		System.out.println("mean                    = " + ps.mean());
		System.out.println("stddev                  = " + ps.stddev());
		System.out.println("95% confidence interval = " + ps.confidenceLo()
				+ ", " + ps.confidenceHi());
	}
}
