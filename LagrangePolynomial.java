public class LagrangePolynomial {
    MathFunction func, iFunc;
    double a,b;
    int num;
    LagrangePolynomial(MathFunction func, double a, double b, int num){
        this.func = func;
        this.a = a;
        this.b = b;
        this.num = num;
        interpolate();
    }

    public MathFunction interpolate(){
        MathFunction math = x1 -> {
            double res=0,l;
            for (int i=0;i<num;i++){
                l=1;
                for(int j=0;j<num;j++){
                    if (i!=j){
                        l= l*(x1-(a+(b-a)*j/num))/((a+(b-a)*i/num)-(a+(b-a)*j/num));
                    }
                }
                res += l*func.calculate((a+(b-a)*i/num));
            }
            return res;
        };
        iFunc = math;
        return math;
    }

    public double count(double x){
        return iFunc.calculate(x);
    }
}
