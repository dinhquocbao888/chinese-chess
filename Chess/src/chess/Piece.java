package chess;;

public abstract class Piece {
	protected int x,y; //vị trí của quân theo hàng và cột
	protected boolean isRed;//Kiểm tra màu của quân
	
	public Piece(int x,int y,boolean isRed)
	{
		this.x=x;
		this.y=y;
		this.isRed = isRed;
	}
	
	public abstract boolean isValidMove(int newX,int newY); // kiểm tra nước đi hợp lệ
	
	public int getX() { return this.x;}
	public int getY() { return this.y;}
	public boolean isRed() { return this.isRed;}
	
	
	public void move(int newX,int newY)
	{
		x = newX;
		y = newY;
	}
	
	public abstract String getImageKey();
}

class Xe extends Piece
{
	public Xe(int x,int y,boolean isRed)
	{
		super(x, y, isRed);
	}
	
	@Override
	public boolean isValidMove(int newX,int newY)
	{
		return x == newX || y == newY;
	}
	@Override
	public String getImageKey()
	{
		return isRed ? "xe_do":"xe_den";
	}
}

class Ma extends Piece
{
	public Ma(int x,int y, boolean isRed)
	{
		super(x,y,isRed);
	}
	@Override
	public boolean isValidMove(int newX, int newY)
	{
		int dx = Math.abs(newX - x);
		int dy = Math.abs(newY - y);
		return(dx==2 && dy==1)||(dx==1 && dy==2);
	}
	
	@Override
	public String getImageKey()
	{
		return isRed ? "ma_do":"ma_den";
	}
}

class Voi extends Piece
{
	public Voi(int x, int y , boolean isRed)
	{
		super(x,y,isRed);
	}
	
	@Override
	public boolean isValidMove(int newX,int newY)
	{
		int dx = Math.abs(newX - x);
		int dy = Math.abs(newY - y);
		boolean TrongLanhTho = isRed ? newY >= 5 : newY <=4;
		return dx==2 && dy==2 && TrongLanhTho;
	}
	
	@Override
	public String getImageKey()
	{
		return isRed ? "voi_do":"voi_den";
	}
}

class Si extends Piece
{
	public Si(int x, int y ,boolean isRed)
	{
		super(x,y,isRed);
	}
	
	@Override
	public boolean isValidMove(int newX,int newY)
	{
		int dx = Math.abs(newX-x);
		int dy = Math.abs(newY-y);
		boolean TrongTrai = newX >= 3 && newX <=5 && (isRed ? newY >=7: newY<=2);
		return dx == 1 && dy == 1 && TrongTrai;
	}
	
	@Override
	public String getImageKey()
	{
		return isRed ? "si_do":"si_den";
	}
}

class Tuong extends Piece
{
	public Tuong(int x, int y, boolean isRed)
	{
		super(x,y,isRed);
	}
	
	@Override
	public boolean isValidMove(int newX, int newY)
	{
		boolean TrongTrai = newX >=3 && newX <=5 && (isRed ? newY >= 7: newY <= 2);
		int dx = Math.abs(newX - x);
		int dy = Math.abs(newY - y);
		return TrongTrai && (dx+dy == 1);
	}
	
	@Override
	public String getImageKey()
	{
		return isRed ? "tuong_do":"tuong_den";
	}
}

class Phao extends Piece
{
	public Phao(int x, int y, boolean isRed)
	{
		super(x,y,isRed);
	}
	
	@Override
	public boolean isValidMove(int newX,int newY)
	{
		return x == newX || y == newY;
	}
	
	@Override
	public String getImageKey()
	{
		return isRed ? "phao_do":"phao_den";
	}
}

class Tot extends Piece
{
	public Tot(int x,int y,boolean isRed)
	{
		super(x,y,isRed);
	}
	
	@Override
	public boolean isValidMove(int newX,int newY)
	{
		int dx = Math.abs(newX - x);
		int dy = newY - y;
		if(isRed)
		{
			if(y<5) return (dx == 1 && dy == 0) || (dx == 0 && dy == -1);// Đã Qua Sông
			else return dx == 0 && dy == -1; //Chưa qua sông
		}
		else
		{
			if(y>4) return(dx == 1 && dy == 0) || (dx == 0 && dy == -1);// đã qua Sông
			else return dx==0 && dy == 1; //Chưa Qua Sông
		}
	}
	
	@Override
	public String getImageKey()
	{
		return isRed ? "tot_do":"tot_den";
	}
	
}
