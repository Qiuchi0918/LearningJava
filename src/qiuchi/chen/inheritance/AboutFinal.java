package qiuchi.chen.inheritance;

interface IBaseInterface {
    private int baseInterfaceMethod() {
        return 2;
    }

    int baseInterfacePublicMethod();
}

interface ISomeInterface extends IBaseInterface {
    public int sdf();

    private void dsasd() {
        //接口可以
    }

    @Override//这个跨接口继承有啥用
    int baseInterfacePublicMethod();
}

public class AboutFinal implements ISomeInterface {
    @Override
    public final int sdf() {
        return 1;
    }

    @Override
    public int baseInterfacePublicMethod() {
        return 0;
    }
}

class TryToInherit extends AboutFinal {
    //@Override
    //public int sdf(){
    //    return 1;
    //}因final声明无法重写
}
