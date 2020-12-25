package qiuchi.chen.exception;

class AboutExecutionOrderOfTryCatchFinally {
    public static int f(int n) {
        try {
            int r = n * n;
            return r;
        } finally {
            if (n == 2) return 0;
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("Try Block");
            return;
        } catch (Exception e) {
            System.out.println("Catch Block");
            return;
        } finally {
            //<!>即使在try或者catch内以经写了return，finally仍然会执行
            //这时如果finally内有更改则会影响return
            //若有return则会替代try和catch中return
            System.out.println("Finally Block");
            return;
        }
        //System.out.println(f(2));
    }
}
