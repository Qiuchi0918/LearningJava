package qiuchi.chen.serialization;


import qiuchi.chen.innerclass.CreateInnerClass;

import java.io.*;

public class ComplicatedClass implements Serializable {

    CreateInnerClass declaration;

    String name;

    int value;

    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(name);
        out.writeInt(value);
    }

    @Serial
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        value = in.readInt();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ComplicatedClass complicatedClass = new ComplicatedClass();
        complicatedClass.name = "asdasd";
        complicatedClass.value = 1;
        complicatedClass.declaration = new CreateInnerClass();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(complicatedClass);

        byte[] arr = outputStream.toByteArray();

        ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(arr));
        ComplicatedClass b = (ComplicatedClass) inputStream.readObject();

        System.out.println(arr.length);
    }

}
