package Engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ErrorMessage extends JFrame{
	private static final long serialVersionUID = 5590355131193810368L;
	
	public ErrorMessage()
	{
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		JLabel bufferJLabel =new JLabel("Wrong Input");
		bufferJLabel.setSize(200, 100);
		this.setSize(400, 300);
		this.setLocation(400,400);
		 JButton buffer= new JButton("ok") ;
		 buffer.addActionListener(new OkListener());
		this.add(new JButton("ok"));
	

		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.validate();
	}

	public ErrorMessage(String a)
	{
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		JLabel bufferJLabel =new JLabel("a");
		bufferJLabel.setSize(200, 100);
		this.setSize(400, 300);
		this.setLocation(400,400);
		 JButton buffer= new JButton("ok") ;
		 buffer.addActionListener(new OkListener());

		this.add(new JButton("ok"));
	
		
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
		System.out.println("jmiaogjonjbmanù");
		this.dispose();
	}
}
