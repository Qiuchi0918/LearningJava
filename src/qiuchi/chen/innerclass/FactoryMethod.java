package qiuchi.chen.innerclass;

interface IService {
    public void doService();
}

interface IServiceFactory {
    IService getService();
}

class Implementation implements IService {
    @Override
    public void doService() {
        System.out.println("Do Service");
    }

    private Implementation() {
    }

    public static IServiceFactory getFactory() {
        return () -> new Implementation();
        //<->这是咋整的?因为接口只有一个方法么：单方法接口
        //return new IServiceFactory() {
        //    @Override
        //    public IService getService() {
        //        return new Implementation();
        //    }
        //};
    }
}

public class FactoryMethod {
    public static void main(String[] args) {
        Implementation.getFactory().getService().doService();
    }
}

