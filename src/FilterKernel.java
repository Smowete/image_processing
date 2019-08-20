



public class FilterKernel {

    ////////////////////////////////  Object Fields  ////////////////////////////////
    private double[][] kernel;

    ////////////////////////////////  Constants  ////////////////////////////////
    private static final int DEFAULT_KERNEL_LENGTH = 5;

    ////////////////////////////////  Preset Kernel Values  ////////////////////////////////
    private static final double[][] KERNEL_VALUE_EDGE_DETECTION = {
        {0,  0,  0,  0,  0},
        {0,  0, -1,  0,  0},
        {0, -1,  4, -1,  0},
        {0,  0, -1,  0,  0},
        {0,  0,  0,  0,  0}
    };
    // private static final double[][] KERNEL_VALUE_EDGE_DETECTION = {
    //     { 0,  0,  0,  0,  0},
    //     { 0,  -1,  0,  1,  0},
    //     { 0,  -2,  0,  2,  0},
    //     { 0,  -1,  0,  1,  0},
    //     { 0,  0,  0,  0,  0}
    // };

    


    ////////////////////////////////  Constructors  ////////////////////////////////

    public FilterKernel(double[][] value) {
        this.kernel = value;
    }

    public FilterKernel(int length) {
        kernel = new double[length][length];
    }

    public FilterKernel() {
        this(DEFAULT_KERNEL_LENGTH);
    }

    ////////////////////////////////  Kernel Value Access Method  ////////////////////////////////

    public double get(int y, int x) {
        return kernel[y][x];
    }

    public int length() {
        return kernel.length;
    }


    ////////////////////////////////  Pre-Created Kernels  ////////////////////////////////

    public static FilterKernel edgeDetectionKernel() {
        return new FilterKernel(KERNEL_VALUE_EDGE_DETECTION);
    }


    
}