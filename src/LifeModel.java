import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Scanner;

import javax.swing.Timer;

public class LifeModel implements ActionListener
{

	/*
	 *  This is the Model component.
	 */
   

	private static int SIZE = 60;
	private LifeCell[][] grid;
	
	LifeView myView;
	Timer timer;

	/** Construct a new model using a particular file */
	public LifeModel(LifeView view, String fileName) throws IOException
	{       
		int r, c;
		grid = new LifeCell[SIZE][SIZE];
		for ( r = 0; r < SIZE; r++ )
			for ( c = 0; c < SIZE; c++ )
				grid[r][c] = new LifeCell();

		if ( fileName == null ) //use random population
		{                                           
			for ( r = 0; r < SIZE; r++ )
			{
				for ( c = 0; c < SIZE; c++ )
				{
					if ( Math.random() > 0.85) //15% chance of a cell starting alive
						grid[r][c].setAliveNow(true);
				}
			}
		}
		else
		{                 
			Scanner input = new Scanner(new File(fileName));
			int numInitialCells = input.nextInt();
			for (int count=0; count<numInitialCells; count++)
			{
				r = input.nextInt();
				c = input.nextInt();
				grid[r][c].setAliveNow(true);
			}
			input.close();
		}

		myView = view;
		myView.updateView(grid);

	}

	/** Constructor a randomized model */
	public LifeModel(LifeView view) throws IOException
	{
		this(view, null);
	}

	/** pause the simulation (the pause button in the GUI */
	public void pause()
	{
		timer.stop();
	}
	
	/** resume the simulation (the pause button in the GUI */
	public void resume()
	{
		timer.restart();
	}
	public void reset() {
		timer.stop();
		
	}
	/** run the simulation (the pause button in the GUI */
	public void run()
	{
		timer = new Timer(50, this);
		timer.setCoalesce(true);
		timer.start();
	}

	/** called each time timer fires */
	public void actionPerformed(ActionEvent e)
	{
		oneGeneration();
		myView.updateView(grid);
	}

	/** main logic method for updating the state of the grid / simulation */
	
	/**
	 * This method determine if the cells are alive.
	 */
	
	private void oneGeneration()
	  {
		  for(int i =0; i< SIZE;i++)
		  {
			  for(int j =0; j < SIZE;j++)
			  {
				  int p = popCount(i,j); 
				
					  if(p ==3)
					  {
						  grid[i][j].setAliveNext(true);
						  grid[i][j].setAliveNow(true );
						  
					  }
					   if(p ==2)
					  {
						  grid[i][j].setAliveNext(grid[i][j].isAliveNow());
						  grid[i][j].setAliveNow(grid[i][j].isAliveNow() );
						  
					  }
					  if(p < 2 || p > 3) 
					  {
						  grid[i][j].setAliveNext(false);
						  grid[i][j].setAliveNow(false);	  
					  }
		    }
		  } 
	  }
	
	/**
	 * This method count the closest cell from one cell. 
	 * @param r the rows 
	 * @param c the columns 
	 * @return  number of Alive cells
	 */
		private int popCount(int r, int c)
		{
			int count =0;
			if ((r != 0 && c != 0) && grid[r-1][c-1].isAliveNow())
			 count++;
			
			if(r!= 0 && grid[r-1][c].isAliveNow())
				count++;
		
			if((r!= 0 && c != SIZE-1)&& grid[r-1][c+1].isAliveNow())
				count++;
	  
			if(c!= 0 && grid[r][c-1].isAliveNow())
				count ++;
			
	  
			if((r!= SIZE-1 && c != 0) && grid[r+1][c-1].isAliveNow())
				count++;
			
			if( r != SIZE-1 && grid[r+1][c].isAliveNow())
				count++;
			
	  
			if(c != SIZE-1 && (grid[r][c+1].isAliveNow()) )
				count++;
			
			if( r!= SIZE-1 && c != SIZE-1  && grid[r+1][c+1].isAliveNow())
				count++;
			
			return count; 
    	  
			}

	
  }
  


