package Engine;
import java.util.*;


public class Time {
	
		protected Timer timerz= new Timer();
		private Niveau niveau;
		private int delay =1000;
		private int period= 1000;
		
		public Time(Niveau niv)
		{
			niveau=niv;
			this.sched();
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
}
