import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private boolean[] mat = null;

	private int n = 0;

	private WeightedQuickUnionUF percolation;

	private WeightedQuickUnionUF fullness;

	private final int[] mx = { -1, 0, 1, 0 };

	private final int[] my = { 0, -1, 0, 1 };

	public Percolation(int n) {
		mat = new boolean[n * n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				mat[i * n + j] = false;
		this.n = n;
		percolation = new WeightedQuickUnionUF(n * n + 2);
		fullness = new WeightedQuickUnionUF(n * n + 1);
	}

	public void open(int i, int j) {
		if (i < 1 || j < 1 || i > n || j > n)
			throw new java.lang.IndexOutOfBoundsException();
		int ni = i - 1, nj = j - 1;
		if (!mat[ni * n + nj]) {
			mat[ni * n + nj] = true;
			for (int m = 0; m < 4; m++) {
				int mi = ni + mx[m];
				int mj = nj + my[m];
				if (mi >= 0 && mj >= 0 && mi < n && mj < n && mat[mi * n + mj]) {
					percolation.union(ni * n + nj, mi * n + mj);
					fullness.union(ni * n + nj, mi * n + mj);
				}
			}
			if (i == 1) {
				percolation.union(ni * n + nj, n * n);
				fullness.union(ni * n + nj, n * n);
			}
			if (i == n)
				percolation.union(ni * n + nj, n * n + 1);
		}
	}

	public boolean isOpen(int i, int j) {
		if (i < 1 || j < 1 || i > n || j > n)
			throw new java.lang.IndexOutOfBoundsException();
		int ni = i - 1, nj = j - 1;
		return mat[ni * n + nj];
	}

	public boolean isFull(int i, int j) {
		if (i < 1 || j < 1 || i > n || j > n)
			throw new java.lang.IndexOutOfBoundsException();
		int ni = i - 1, nj = j - 1;
		return mat[ni * n + nj] && fullness.connected(ni * n + nj, n * n);
	}

	public int numberOfOpenSites() {
		int count = 0;
		for (boolean isopen : mat)
			if (isopen) count++;
		return count;
	}

	public boolean percolates() {
		return percolation.connected(n * n, n * n + 1);
	}
}
