import javax.swing.JLabel;
public class BlinkFrame extends Thread{
	private JLabel label;
	private boolean bool = false;
	public BlinkFrame(JLabel label){
		this.label = label;
	}
	public void run(){
		while(true){
			if(bool == true){
				bool = false;
			}else{
				bool = true;
			}
			label.setVisible(bool);
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){}
		}
	}
}