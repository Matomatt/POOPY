package Engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Closeable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ErrorMessage extends JFrame{
	public ErrorMessage()
	{
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		JLabel bufferJLabel =new JLabel("Wrong Input");
		bufferJLabel.setSize(200, 100);
		this.setSize(400, 300);
		this.setLocation(400,400);
		this.add(new JButton("ok"));
	
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.validate();
	}
	private class OkListener implements ActionListener  //?
	{
		public void actionPerformed(ActionEvent e)
		{
			close();
		}
	}
	private void close()
	{
		this.dispose();
	}
}
