package yincheng.tinytank.android_Q_A._101_200._101;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class ExternalizableTest implements Externalizable {

	private transient String content = "是的，我将会被序列化，不管我是否被transient关键字修饰";

	public static void main(String[] args) throws Exception {

		ExternalizableTest et = new ExternalizableTest();
		ObjectOutput out = new ObjectOutputStream(new FileOutputStream(
				new File("test")));
		out.writeObject(et);

		ObjectInput in = new ObjectInputStream(new FileInputStream(new File(
				"test")));
		et = (ExternalizableTest) in.readObject();
		System.out.println(et.content);

		out.close();
		in.close();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(content);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		content = (String) in.readObject();
	}
}
