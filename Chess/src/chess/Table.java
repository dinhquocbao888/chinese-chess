package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.HashMap;


public class Table extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private final int Cell_Size = 60; // kích thước ô cờ
	private final int TABLE_COLS = 9; // số ô cờ theo chiều rộng 
	private final int TABLE_ROWS = 10; //số ô cờ theo chiều dài
	private final int WIDTH = 9 * Cell_Size; // chiều rộng bàn cờ
	private final int HEIGHT = 10 * Cell_Size; // chiều dài bàn cờ
	
	//Offset là 1 thành phần giúp cho việc căn giữa bàn cờ trở nên dễ dàng hơn
	private final int MARGIN_X = 60;
	private final int MARGIN_Y = 60;
	private final int offset = 30;
	
	//Quân cờ được chọn
	private Piece selectedPiece = null;
	
	private boolean isRedTurn = true;
	
	ArrayList<Piece> pieces = new ArrayList<Piece>();
	private HashMap<String, BufferedImage> pieceImages = new HashMap<>();
	
	public Table()
	{
		try
		{
			//lệch bắt sự kiện click chuột
			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e)
				{
					int clickX = (e.getX() - MARGIN_X + offset)/Cell_Size;
					int clickY = (e.getY() - MARGIN_Y + offset)/Cell_Size;
					handleClick(clickX,clickY);
					System.out.println("vi tri click x: "+clickX);
					System.out.println("vi tri click y: "+clickY);
				}
			});
			
			//lệch load ảnh quân cờ
			pieceImages.put("xe_do", ImageIO.read(new File("D:\\Dinh Quoc Bao\\Java\\Chess\\image\\chinese_red_chariot.png")));
			pieceImages.put("xe_den", ImageIO.read(new File("D:\\Dinh Quoc Bao\\Java\\Chess\\image\\chinese_black_chariot.png")));
			pieceImages.put("ma_do", ImageIO.read(new File("D:\\Dinh Quoc Bao\\Java\\Chess\\image\\chinese_red_horse.png")));
			pieceImages.put("ma_den", ImageIO.read(new File("D:\\Dinh Quoc Bao\\Java\\Chess\\image\\chinese_black_horse.png")));
			pieceImages.put("voi_do", ImageIO.read(new File("D:\\Dinh Quoc Bao\\Java\\Chess\\image\\chinese_red_elephant.png")));
			pieceImages.put("voi_den", ImageIO.read(new File("D:\\Dinh Quoc Bao\\Java\\Chess\\image\\chinese_black_elephant.png")));
			pieceImages.put("si_do", ImageIO.read(new File("D:\\Dinh Quoc Bao\\Java\\Chess\\image\\chinese_red_guard.png")));
			pieceImages.put("si_den", ImageIO.read(new File("D:\\Dinh Quoc Bao\\Java\\Chess\\image\\chinese_black_guard.png")));
			pieceImages.put("tuong_do", ImageIO.read(new File("D:\\Dinh Quoc Bao\\Java\\Chess\\image\\chinese_red_general.png")));
			pieceImages.put("tuong_den", ImageIO.read(new File("D:\\Dinh Quoc Bao\\Java\\Chess\\image\\chinese_black_general.png")));
			pieceImages.put("phao_do", ImageIO.read(new File("D:\\Dinh Quoc Bao\\Java\\Chess\\image\\chinese_red_cannon.png")));
			pieceImages.put("phao_den", ImageIO.read(new File("D:\\Dinh Quoc Bao\\Java\\Chess\\image\\chinese_black_cannon.png")));
			pieceImages.put("tot_do", ImageIO.read(new File("D:\\Dinh Quoc Bao\\Java\\Chess\\image\\chinese_red_soldier.png")));
			pieceImages.put("tot_den", ImageIO.read(new File("D:\\Dinh Quoc Bao\\Java\\Chess\\image\\chinese_black_soldier.png")));
			//hàm thêm quân vào vị trí mặt định
			initTable();
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		//Vẽ Nền
		g.setColor(new Color(255,228,181)); //Mô phỏng bàn cờ gỗ
		g.fillRect(MARGIN_X, MARGIN_Y, WIDTH, HEIGHT);
		
		g.setColor(Color.BLACK);
		
		//Vẽ các đường ngang
		for(int i=0;i<TABLE_ROWS;i++)
		{
			g.drawLine(MARGIN_X, MARGIN_Y + i*Cell_Size, MARGIN_X + (TABLE_COLS - 1) *Cell_Size, MARGIN_Y + i*Cell_Size);
		}
		//Vẽ các đường dọc
		for(int i =0;i<9;i++)
		{
			int x = MARGIN_X + i*Cell_Size;
			g.drawLine(x, MARGIN_Y, x, MARGIN_Y + 4*Cell_Size);
			g.drawLine(x,MARGIN_Y + 5*Cell_Size, x,MARGIN_Y + 9*Cell_Size);
		}
		
		
		//Vẽ chữ Sông
		g.setFont(new Font("Serif",Font.BOLD,24));
		g.drawString("哈 河",MARGIN_X + 4*Cell_Size - 24,MARGIN_Y + 5*Cell_Size-20);
		
		//Vẽ 2 đường chéo doanh trại tướng
		//Trên
		g.drawLine(MARGIN_X + 3*Cell_Size, MARGIN_Y ,MARGIN_X + 5*Cell_Size,MARGIN_Y + 2*Cell_Size);
		g.drawLine(MARGIN_X +  5*Cell_Size,MARGIN_Y ,MARGIN_X + 3*Cell_Size, MARGIN_Y + 2*Cell_Size);
		
		//Dưới
		g.drawLine(MARGIN_X + 3*Cell_Size,MARGIN_Y + 7*Cell_Size,MARGIN_X + 5*Cell_Size,MARGIN_Y + 9*Cell_Size);
		g.drawLine(MARGIN_X + 5*Cell_Size,MARGIN_Y + 7*Cell_Size,MARGIN_X + 3*Cell_Size,MARGIN_Y + 9*Cell_Size);
		
		
		
		for(Piece p : pieces)
		{
			BufferedImage img = pieceImages.get(p.getImageKey());
			if(img != null)
			{
				int drawX = MARGIN_X + p.getX() * Cell_Size - offset;
				int drawY = MARGIN_Y + p.getY() * Cell_Size - offset;
				g.drawImage(img, drawX, drawY, Cell_Size, Cell_Size, null);
			}
		}
		
		if(selectedPiece != null)
		{
			g.setColor(new Color(255,0,0,128));
			for(Point p : possibleMoves)
			{
				int hightlightX = MARGIN_X + p.x*Cell_Size - offset + 15;
				int hightlightY = MARGIN_Y + p.y*Cell_Size - offset + 15;
				g.fillOval(hightlightX,hightlightY,Cell_Size/2,Cell_Size/2);
			}
		}
		
		if(selectedPiece != null)
		{
			g.setColor(Color.RED);
			int hightlightX = MARGIN_X + selectedPiece.getX()*Cell_Size;
			int hightlightY = MARGIN_Y + selectedPiece.getY()*Cell_Size;
			g.drawRect(hightlightX-offset,hightlightY-offset,Cell_Size,Cell_Size);
			g.drawRect(hightlightX-offset + 1,hightlightY-offset + 1,Cell_Size - 2,Cell_Size - 2);
		}
	}
	
	//Đặt trước kích thước mặc định của bàn cờ
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(WIDTH + 2*MARGIN_X,HEIGHT + 2*MARGIN_Y);
	}
	
	//Đặt Quân vào Bàn CỜ
	private void initTable()
	{
		//Kiểm Tra Màu Quân
		boolean RED = true;
		boolean BLACK = false;
		
		//Quân đỏ ở Dưới Sông
		pieces.add(new Xe(0,9,RED));
		pieces.add(new Ma(1,9,RED));
		pieces.add(new Voi(2,9,RED));
		pieces.add(new Si(3,9,RED));
		pieces.add(new Tuong(4,9,RED));
		pieces.add(new Si(5,9,RED));
		pieces.add(new Voi(6,9,RED));
		pieces.add(new Ma(7,9,RED));
		pieces.add(new Xe(8,9,RED));
		
		pieces.add(new Phao(1,7,RED));
		pieces.add(new Phao(7,7,RED));
		for(int i = 0; i< 9;i+=2)
		{
			pieces.add(new Tot(i,6,RED));
		}
		
		// Quân Đen ở Bên Kia Sông
		pieces.add(new Xe(0,0,BLACK));
		pieces.add(new Ma(1,0,BLACK));
		pieces.add(new Voi(2,0,BLACK));
		pieces.add(new Si(3,0,BLACK));
		pieces.add(new Tuong(4,0,BLACK));
		pieces.add(new Si(5,0,BLACK));
		pieces.add(new Voi(6,0,BLACK));
		pieces.add(new Ma(7,0,BLACK));
		pieces.add(new Xe(8,0,BLACK));
		
		pieces.add(new Phao(1,2,BLACK));
		pieces.add(new Phao(7,2,BLACK));
		for(int i = 0;i<9;i+=2)
		{
			pieces.add(new Tot(i,3,BLACK));
		}
	}
	
	ArrayList<Point> possibleMoves = new ArrayList<Point>();
	
	private void handleClick(int x, int y)
	{
		if(selectedPiece == null)
		{
			boolean found = false;
			//chọn quân
			for(Piece p : pieces)
			{
				if(p.isRed() == isRedTurn)
				{
					if(p.getX() == x && p.getY() == y)
					{
						selectedPiece = p;
						found = true;
						possibleMoves.clear();
						for(int i=0;i<TABLE_COLS;i++)
						{
							for(int j = 0;j<TABLE_ROWS;j++)
							{
								if(p.isValidMove(i, j))
								{
									possibleMoves.add(new Point(i,j));
								}
							}
						}
						System.out.println("Chọn quân: " + p.getImageKey());
						break;
					}
				}
				else
				{
					System.out.println("không đúng lượt của bạn");
				}
			}
			if(!found)
			{
				System.out.println("bạn đã click vào ô trống, không có quân để chọn");
			}
		}
		else
		{
			System.out.println("Đã chọn quân: " + selectedPiece.getImageKey());
			System.out.println("click Đến ô: ("+x+","+y+")");
			//ô muốn di chuyển
			if(selectedPiece.isValidMove(x, y))
			{
				System.out.println("nước đi hợp lệ");
				pieces.removeIf(p->p.getX()==x && p.getY() == y && p.isRed() != selectedPiece.isRed());
				
				//di chuyển
				selectedPiece.move(x, y);
				System.out.println("di chuyển đến: ("+ x +","+ y +")");
				selectedPiece = null;
				isRedTurn = !isRedTurn;
			}
			else if(selectedPiece.getX()==x && selectedPiece.getY()==y)
			{
				System.out.println("Hủy chọn quân");
				selectedPiece = null;
				
			}
			else
			{
				System.out.println(" => Nước đi không hợp lệ");
			}
			possibleMoves.clear();
		}
		
		repaint();
	}
	
	public void ResetGame()
	{
		initTable();
		isRedTurn = true;
		possibleMoves.clear();
		selectedPiece = null;
		repaint();
	}
	
	public ArrayList<Piece> getPieces()
	{
		return pieces;
	}
}
