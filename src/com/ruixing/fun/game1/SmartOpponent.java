package com.ruixing.fun.game1;

/**
 * Algorithm implementation of player for game "5-in-a-row"
 * With the implemented algorithm, the player first tries to stop the other 
 * player making 5 connected pieces, at the same time tries to make 5 connected pieces.
 * Always defending first, then attacking. 
 * 
 * @author Ruixing
 */

import com.nosto.fun.game1.ArenaPosition;
import com.nosto.fun.game1.Piece;
import com.nosto.fun.game1.Player;

public class SmartOpponent implements Player, Cloneable {
	Piece myPiece;
    String name;
    boolean firstMove = true;
    private static final int SIZE = 18;     // size of the board
    
    /** Creates a new instance of SmartOpponent */
    public SmartOpponent(String name) {
        this.name = name;
    }

    public void setSide(Piece p) {
        myPiece = p;
    }

    public Piece getSide() {
        return myPiece;
    }
    
    public Piece getOpponentSide() {
    	if( getSide() == Piece.CROSS ){
    		return Piece.ROUND;
    	}
    	else
    	{
    		return Piece.CROSS;
    	}
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ArenaPosition move(Piece[][] board, ArenaPosition lastPosition) {
    	// first move is a random move
    	if( firstMove )
    	{
    		if( checkIfFirstMove(board.clone(), getOpponentSide() ) )
			{
	    		// first move, make a random move
	    		if (Math.random() * 1000 <= 3) {
	                return new ArenaPosition(-23235, 0);
	            }
	            while (true) {
	                int x = (int) (Math.random() * board.length);
	                int y = (int) (Math.random() * board.length);
	                if (board[x][y] == null) {
	                	firstMove = false;
	                    return new ArenaPosition(x, y);
	                }
	            }
			}
    	}
        int max_my_piece = 0;
        int max_opponent_piece = 0;
        int max_temp = 0;
        int max = 0;
        int returned_x = 0;
        int returned_y = 0;
        
        	// algorithm to decide where to make next move 
        	for(int i=0;i<SIZE;i++)
        	{
        		for(int j=0;j<SIZE;j++)
        		{

        			if( board[i][j] == null)
        			{
        				
        				// check its neighboring positions, how many other play's pieces are connected
        				max_opponent_piece = checkMaxValue(i,j,board.clone(),getOpponentSide());
        				// check its neighboring positions, how many play's own pieces are connected
        				max_my_piece = checkMaxValue(i,j,board.clone(),getSide());
        				// compare to decide defending or attacking
        				max_temp = Math.max(max_opponent_piece,  max_my_piece);
        				if(max_temp >= max)
        				{
        					max = max_temp;
        					returned_x = i;
        					returned_y = j;
        				}
        				
        			}
        			
        		}
        		
        	}
                       
            // end of algorithm

            return new ArenaPosition(returned_x, returned_y);

      
    }

    public String toString() {
        return getName();
    }

    public int checkMaxValue(int x, int y, Piece[][] board, Piece cross_or_round){
    	int number_of_neighbors = 5;
    	int FIVE_IN_A_ROW = 5;
    	int number_of_connected = 0, max_num, max_temp=0;
    	int x_tmp=x,y_tmp=y;
    	int x_temp1=x_tmp,y_temp1=y_tmp;

    	// check horizontal direction, how many connected marks
    	// right side
    	for(int i= 0; i<number_of_neighbors; i++)
    	{
    		x_temp1 += 1; 
    		if( x_temp1 >= SIZE)
    		{
    			// out of the border of the board
    			break;      
    		}
    		if( board[x_temp1][y_temp1] == cross_or_round)
    		{
    			number_of_connected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	// left side
    	x_temp1=x_tmp; // reset the x_temp1 as x
    	for(int i=0; i<number_of_neighbors; i++)
    	{
    		x_temp1 -= 1;
    		if( x_temp1 < 0)
    		{
    			// out of the border of the board
    			break;         
    		}
    		if( board[x_temp1][y_temp1] == cross_or_round)
    		{
    			number_of_connected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	if(number_of_connected<FIVE_IN_A_ROW)
    	{    		
    		max_temp = number_of_connected;
    	}
    	
    	// check vertical direction, how many connected marks
    	// upward side
    		// 	reset
    	x_temp1 = x_tmp; 	
    	y_temp1 = y_tmp; 	
    	number_of_connected = 0;   
    	for(int i=0; i<number_of_neighbors; i++)
    	{
    		y_temp1-=1;
    		if(y_temp1<0)
    		{
    			break;
    		}
    		if(board[x_temp1][y_temp1]== cross_or_round)
    		{
    			number_of_connected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	// downward side 
    		// reset
    	y_temp1 = y_tmp;
    	for(int i=0; i<number_of_neighbors; i++)
    	{
    		y_temp1 += 1;
    		if(y_temp1 >= SIZE )
    		{
    			break;
    		}
    		if(board[x_temp1][y_temp1]== cross_or_round)
    		{
    			number_of_connected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	if(number_of_connected>max_temp & number_of_connected<FIVE_IN_A_ROW )
    	{
    		max_temp = number_of_connected;
    	}
    	// check backward slash direction ("\"), how many connected marks
    	// up left direction
    	x_temp1 = x_tmp;
    	y_temp1 = y_tmp;
    	number_of_connected = 0;
    	for(int i=0; i<number_of_neighbors; i++)
    	{
    		x_temp1 -= 1;
    		y_temp1 -= 1;
    		if(y_temp1<0 || x_temp1<0 )
    		{
    			break;
    		}
    		if(board[x_temp1][y_temp1] == cross_or_round)
    		{
    			number_of_connected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	// down right direction
    	x_temp1 = x_tmp;
    	y_temp1 = y_tmp;    	
    	for(int i=0; i<number_of_neighbors; i++)
    	{
    		x_temp1 += 1;
    		y_temp1 += 1;
    		if(y_temp1>=SIZE || x_temp1>=SIZE )
    		{
    			break;
    		}
    		if(board[x_temp1][y_temp1] == cross_or_round)
    		{
    			number_of_connected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	if(number_of_connected>max_temp & number_of_connected<FIVE_IN_A_ROW )
    	{
    		max_temp = number_of_connected;
    	}
    	// check forward slash direction ("/"), how many connected marks
    	// up right direction
    	x_temp1 = x_tmp;
    	y_temp1 = y_tmp;
    	number_of_connected = 0;
    	for(int i=0; i<number_of_neighbors; i++)
    	{
    		x_temp1 += 1;
    		y_temp1 -= 1;
    		if(y_temp1<0 || x_temp1>=SIZE )
    		{
    			break;
    		}
    		if(board[x_temp1][y_temp1] == cross_or_round)
    		{
    			number_of_connected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	// down left direction
    	x_temp1 = x_tmp;
    	y_temp1 = y_tmp;    	
    	for(int i=0; i<number_of_neighbors; i++)
    	{
    		x_temp1 -= 1;
    		y_temp1 += 1;
    		if(y_temp1>=SIZE || x_temp1<0 )
    		{
    			break;
    		}
    		if(board[x_temp1][y_temp1] == cross_or_round)
    		{
    			number_of_connected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	if(number_of_connected>max_temp & number_of_connected<FIVE_IN_A_ROW )
    	{
    		max_temp = number_of_connected;
    	}
    	max_num = max_temp;
    	return max_num;
    }
    
    public boolean checkIfFirstMove(Piece[][] board, Piece my_side){
    	for(int i=0;i<SIZE;i++)
    	{
    		for(int j=0;j<SIZE;j++)
    		{
    			if( board[i][j] == my_side )
    			{
    				// not the first move, return false
    				return false;
    			}
    		}
    	}
    	return true;
    }
}
