package Engine;
import java.util.*;


public class Time {

	protected Timer timerz= new Timer();
	private Niveau niveau;
	private int delay =1000;
	private int period= 1000;
	boolean active=true;

	public Time(Niveau niv)
	{
		niveau=niv;
		this.sched();
		this.pPressed();
	}

	public void sched()
	{
		timerz.schedule( new decrease(), delay, period);
	}
	private class decrease extends TimerTask
	{
		@Override
		public void run() {

			niveau.timergestion();
		}

	}
	public void pPressed()
	{
		if(active==true)
		{
			stop();
			active=false;
		}
		else
		{
			active=true;
			restart();
		}
	}

	public void stop()
	{
		timerz.cancel();
	}

	public void restart()
	{
		timerz= new Timer();
		sched();
	}
	
	public void cancel()
	{
		timerz.cancel();
	}
}
