package jp.co.sample.emp_management;

public class SampleThread extends Thread {
	@Override
    public void run() {
        mBathroom.openDoor(mName);
    }
}
