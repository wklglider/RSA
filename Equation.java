import java.math.BigDecimal;
import java.math.RoundingMode;

public class Equation {
	
    public static void main(String[] args) {
    	BigDecimal[][] matrix = new BigDecimal[18][19];
    	for (int i = 0; i < 18; i++) {
    		matrix[i][0] = BigDecimal.valueOf(1);
    		for (int j = 1; j < 18; j++) {
    			matrix[i][j] = BigDecimal.valueOf(i + 1).pow(j);
        	}
    	}
    	matrix[0][18] = new BigDecimal("28");
    	matrix[1][18] = new BigDecimal("2860041");
    	matrix[2][18] = new BigDecimal("2116022100");
    	matrix[3][18] = new BigDecimal("224596648591");
    	matrix[4][18] = new BigDecimal("8320818717276");
    	matrix[5][18] = new BigDecimal("158924894059653");
    	matrix[6][18] = new BigDecimal("1924023197109316");
    	matrix[7][18] = new BigDecimal("16693822154485035");
    	matrix[8][18] = new BigDecimal("112339759835331996");
    	matrix[9][18] = new BigDecimal("618757074797428801");
    	matrix[10][18] = new BigDecimal("2898445532433210228");
    	matrix[11][18] = new BigDecimal("11878127680902032391");
    	matrix[12][18] = new BigDecimal("43509687966681456220");
    	matrix[13][18] = new BigDecimal("144849377714895261501");
    	matrix[14][18] = new BigDecimal("444093437070594795876");
    	matrix[15][18] = new BigDecimal("1267301409428695348003");
    	matrix[16][18] = new BigDecimal("3395560880695436511516");
    	matrix[17][18] = new BigDecimal("8603953120467932950905");
        BigDecimal[] rst = new Equation().solveEquation(matrix, 0, RoundingMode.HALF_UP);
        System.out.println("M = " + rst[0]);
        for (int i = 1; i < rst.length; ++i) {
            System.out.println("s[" + i + "] = " + rst[i]);
        }
    }
    
    public BigDecimal[] solveEquation(BigDecimal[][] matrix, int scale, RoundingMode roundingMode){
        if(isNullOrEmptyMatrix(matrix)){
            return new BigDecimal[0];
        }
        BigDecimal[][] triangular = elimination(matrix, scale, roundingMode);
        return substitutionUpMethod(triangular, scale, roundingMode);
    }
    
    private BigDecimal[][] elimination(BigDecimal[][] matrix, int scale, RoundingMode roundingMode) {
        if(isNullOrEmptyMatrix(matrix) || matrix.length != matrix[0].length - 1){
            return new BigDecimal[0][0];
        }
        int matrixLine = matrix.length;
        for (int i = 0; i < matrixLine - 1; ++i) {
            for (int j = i + 1; j < matrixLine; ++j) {
                for (int k = i + 1; k <= matrixLine; ++k) {
                    matrix[j][k] = matrix[j][k].subtract((matrix[i][k].divide(matrix[i][i], scale, roundingMode).multiply(matrix[j][i])));
                }
                matrix[j][i] = BigDecimal.ONE;
            }
        }
        return matrix;
    }
    
    private BigDecimal[] substitutionUpMethod(BigDecimal[][] matrix, int scale, RoundingMode roundingMode) {
        int row = matrix.length;
        for (int i = 0; i < row; ++i) {
            if (matrix[i][i].equals(BigDecimal.ZERO.setScale(scale))) {
                return new BigDecimal[0];
            }
        }
        BigDecimal[] result = new BigDecimal[row];
        for (int i = 0; i < result.length; ++i) {
            result[i] = BigDecimal.ONE;
        }
        BigDecimal tmp;
        for (int i = row - 1; i >= 0; --i) {
            tmp = BigDecimal.ZERO;
            int j = row - 1;
            while (j > i) {
                tmp = tmp.add(matrix[i][j].multiply(result[j]));
                --j;
            }
            result[i] = matrix[i][row].subtract(tmp).divide(matrix[i][i], scale, roundingMode);
        }
        return result;
    }
    
    private static boolean isNullOrEmptyMatrix(BigDecimal[][] matrix){
        if(matrix == null || matrix.length == 0){
            return true;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        for(int i = 0; i < row; ++i){
            for(int j = 0; j < col; ++j){
                if(matrix[i][j] == null){
                    return true;
                }
            }
        }
        return false;
    }
}
