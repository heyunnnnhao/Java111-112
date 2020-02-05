import java.io.*;

public class MatrixDriver{

	public static void main(String[] args) throws IOException{

		System.out.println("Enter name of file containing first matrix:");
		String matrixFilename1 = IO.readString();
		double[][] matrix1 = readMatrixFromFile(matrixFilename1);

		System.out.println("Enter name of file containing second matrix:");
		String matrixFilename2 = IO.readString();
		double[][] matrix2 = readMatrixFromFile(matrixFilename2);

		double[][] product = multiply(matrix1, matrix2);

		if (product != null){
			System.out.println("product matrix:");
			printMatrix(product);
		}else{
			System.out.println("multiply() returned null to indicate bad input");
		}
	}

	public static double[][] readMatrixFromFile(String filename) throws IOException{

		BufferedReader br;
		String line;
		int numrows, numcols, colsOnLine;
		double[][] matrix;
		String[] lineparts;

		br = new BufferedReader(new FileReader(filename));
		numrows = 0;
		numcols = -1;
		while ((line = br.readLine()) != null){
			numrows++;
			colsOnLine = line.trim().split("\\s+").length;
			if (numcols == -1) numcols = colsOnLine;
			if (colsOnLine != numcols){
				System.err.println("Badly formatted matrix file: " + filename);
				return null;
			}
		}
		br.close();

		matrix = new double[numrows][numcols];
		br = new BufferedReader(new FileReader(filename));
		for (int row = 0 ; row < numrows ; row++){
			lineparts = br.readLine().trim().split("\\s+");
			for (int col = 0 ; col < numcols ; col++){
				matrix[row][col] = Double.parseDouble(lineparts[col]);
			}
		}
		br.close();

		return matrix;
	}

	public static void printMatrix(double[][] matrix){

		for (int row = 0 ; row < matrix.length ; row++){
			for (int col = 0 ; col < matrix[0].length ; col++){
				System.out.print(matrix[row][col] + "\t");
			}
			System.out.println();
		}
	}

	public static double[][] multiply(double[][] A, double[][] B){
	
		int rowA = A.length;
		int rowB = B.length;
		int columnA = A[0].length;
		int columnB = B[0].length;	
		double[][] C = new double[A.length][B[0].length];	
		
		if ( columnA != rowB ) return null;
		
		for(int i = 0; i < rowA; i++){
			for(int j = 0; j < columnB; j++){
				for(int n = 0; n < columnA; n++){
					C[i][j] += A[i][n] * B[n][j];
				}
			}
		}
		
		return C;
    }
}