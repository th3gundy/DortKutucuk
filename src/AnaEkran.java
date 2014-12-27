import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class AnaEkran extends JFrame implements MouseListener{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private final JButton [][] kare;
	private final JLabel txtBilPuan;
	private final JLabel txtInsPuan;
	public int matris[][] = new int[12][12]; //1:insan 5:bilgisayar
	public int oynananKare = 1;
	public int insX,insY;	//insanin yapacagi hamle
	public int bilX,bilY;	//bilgisayarin yapacagi hamle
	public int sonX,sonY;	//puanlandirma icin kullaniliyor
	public int puanIns = 0, puanBil = 0;	//tahminler icin kullaniliyor
	public int insPuan = 0, bilPuan = 0;	//puanlandirma icin kullaniliyor

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					AnaEkran frame = new AnaEkran();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AnaEkran() {
		
		setTitle("Dört Kutucuk");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 703);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBilgisayar = new JLabel("Bilgisayar :");
		lblBilgisayar.setBounds(35, 637, 100, 15);
		contentPane.add(lblBilgisayar);
		
		txtBilPuan = new JLabel("");
		txtBilPuan.setBounds(147, 637, 70, 15);
		contentPane.add(txtBilPuan);
		
		JLabel lblInsan = new JLabel("Insan :");
		lblInsan.setBounds(372, 637, 70, 15);
		contentPane.add(lblInsan);
		
		txtInsPuan = new JLabel("");
		txtInsPuan.setBounds(454, 637, 70, 15);
		contentPane.add(txtInsPuan);
		
		kare = new JButton[12][12];
		int satir = 20;
		int sutun = 25;
		for(int i=0;i<12;i++) {
			sutun = 25;
			for(int j=0;j<12;j++) {
				kare[i][j] = new JButton("");
				kare[i][j].addMouseListener(this);
				kare[i][j].setBackground(Color.lightGray);
            	kare[i][j].setEnabled(false);
				kare[i][j].setBounds(sutun, satir, 48, 48);
                contentPane.add(kare[i][j]);
                sutun = sutun+50;
			}
			satir = satir+50;
		}
		for(int i=0;i<12;i++)
			for(int j=0;j<12;j++)
				matris[i][j] = 0;
		Random rnd = new Random();
		int sat = rnd.nextInt(12);
		int sut = rnd.nextInt(12);
		matris[sat][sut] = 5;	//bilgisayar ilk hamleyi yapti
		tahtaGuncelle();		//uzerinde islem yapilan matrisi ekrana yazdir
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		for(int i=0;i<12;i++)
			for(int j=0;j<12;j++) {
				if (kare[i][j]==evt.getComponent() && matris[i][j]==0){
					tahtaGuncelle();
					matris[i][j] = 1;
					if ( skorHesapla(i, j) == 5) {
						System.out.println("\n\nOyuncu 5 puan kazandi\n\n");
						insPuan += 5;
					}
					oynananKare++;
					bilgisayarOyna();
					System.out.println("gelen: "+skorHesapla(sonX, sonY));
					if ( skorHesapla(sonX, sonY) == 5) {
						System.out.println("\n\nBilgisayar 5 puan kazandi\n\n");
						bilPuan += 5;
					}
					oynananKare++;
					tahtaGuncelle();
					System.out.println();
					for(int k=0;k<12;k++) {
						for(int l=0;l<12;l++)
							System.out.print(matris[k][l]+" ");
						System.out.println();
					}
					System.out.println(oynananKare);
				}
				if(oynananKare-1==144) {
					System.out.println("Oyun bitti");
					if(insPuan==bilPuan)
						JOptionPane.showMessageDialog(null, "Berabere!", "Oyun Bitti", JOptionPane.INFORMATION_MESSAGE);
					else if(insPuan>bilPuan)
						JOptionPane.showMessageDialog(null, "Tebrikler, Kazandin!", "Oyun Bitti", JOptionPane.QUESTION_MESSAGE);
					else if(bilPuan>insPuan)
						JOptionPane.showMessageDialog(null, "Kaybettin!", "Oyun Bitti", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void bilgisayarOyna() {
		int enbIns = -10;
		int enbBil = -10;
		int bilPuan = 0, insPuan = 0;
		int araSonuc = 0;
		for(int i=0;i<12;i++)
			for(int j=0;j<12;j++)
				if(matris[i][j]==0) {
					matris[i][j] = 5;	//farazi bilgisayar hamlesi
					for(int k=0;k<12;k++)
						for(int l=0;l<12;l++) {
							araSonuc = 0;
							if(matris[k][l]==0) {
								matris[k][l] = 1;	//farazi insan hamlesi
								bilPuan = bilPuanHesapla(i,j);
								insPuan = puanHesapla(k,l);
								if(enbBil<bilPuan) {
									enbBil = bilPuan;
									bilX = i;
									bilY = j;
								}
								if(enbIns<insPuan) {
									enbIns = insPuan;
									insX = k;
									insY = l;
								}
								matris[k][l] = 0;
							}
						}
					matris[i][j] = 0;
				}
		System.out.println("enbBil:"+enbBil+" enbIns:"+enbIns+" x:"+bilX+" y:"+bilY);
		System.out.println("insX:"+insX+" insY:"+insY);
		if(enbBil<enbIns) {
			matris[insX][insY] = 5;
			sonX = insX;
			sonY = insY;
		}
		else {
			matris[bilX][bilY] = 5;
			sonX = bilX;
			sonY = bilY;
		}
	}
	
	public int puanHesapla(int x, int y) {
		//kullanicinin verilen koordinattan alabilecegi en fazla puan
		int araSonuc = 0;
		int araSonuc2 = 0;
		int enb = 0;
		//burada verilen koordinat cevresinde yapilabilecek butun hamleler degerlendiriliyor
		if(x-1>-1 && y-1>-1) {
			araSonuc2 = matris[x-1][y-1]+matris[x-1][y]+matris[x][y-1];
			if(araSonuc2<5 && matris[x-1][y-1]==1 && matris[x-1][y]==1 && matris[x][y-1]==1) {
				araSonuc = 4;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x-1][y]==1 && matris[x][y-1]==1) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x-1][y-1]==1 && matris[x][y-1]==1) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x-1][y-1]==1 && matris[x-1][y]==1) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x-1][y-1]==1) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x-1][y]==1) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x][y-1]==1) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
		}
		if(x-1>-1 && y+1<12) {
			araSonuc2 = matris[x-1][y]+matris[x-1][y+1]+matris[x][y+1];
			if(araSonuc2<5 && matris[x-1][y]==1 && matris[x-1][y+1]==1 && matris[x][y+1]==1) {
				araSonuc = 4;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x-1][y]==1 && matris[x-1][y+1]==1) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x-1][y+1]==1 && matris[x][y+1]==1) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x][y+1]==1 && matris[x-1][y]==1) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x-1][y+1]==1) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x-1][y]==1) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x][y+1]==1) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
		}
		if(x+1<12 && y-1>-1) {
			araSonuc2 = matris[x][y-1]+matris[x+1][y-1]+matris[x+1][y];
			if(araSonuc2<5 && matris[x][y-1]==1 && matris[x+1][y-1]==1 && matris[x+1][y]==1) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x][y-1]==1 && matris[x+1][y-1]==1) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x][y-1]==1 && matris[x+1][y]==1) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x+1][y-1]==1 && matris[x+1][y]==1) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x][y-1]==1) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x+1][y-1]==1) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x+1][y]==1) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
		}
		if(x+1<12 && y+1<12) {
			araSonuc2 = matris[x][y+1]+matris[x+1][y]+matris[x+1][y+1];
			if(araSonuc2<5 && matris[x][y+1]==1 && matris[x+1][y]==1 && matris[x+1][y+1]==1) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x][y+1]==1 && matris[x+1][y]==1) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x][y+1]==1 && matris[x+1][y+1]==1) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x+1][y]==1 && matris[x+1][y+1]==1) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x][y+1]==1) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x+1][y]==1) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2<5 && matris[x+1][y+1]==1) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
		}
		return enb;
	}
	
	public int bilPuanHesapla(int x, int y) {
		//bilgisayarin verilen koordinattan alabilecegi en fazla puan
		int araSonuc = 0;
		int araSonuc2 = 0;
		int enb = 0;
		//burada verilen koordinat cevresinde yapilabilecek butun hamleler degerlendiriliyor
		if(x-1>-1 && y-1>-1) {
			araSonuc2 = matris[x-1][y-1]+matris[x-1][y]+matris[x][y-1];
			if(araSonuc2%5==0 && matris[x-1][y-1]==5 && matris[x-1][y]==5 && matris[x][y-1]==5) {
				araSonuc = 4;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x-1][y]==5 && matris[x][y-1]==5) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x-1][y-1]==5 && matris[x][y-1]==5) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x-1][y-1]==5 && matris[x-1][y]==5) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x-1][y-1]==5) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x-1][y]==5) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x][y-1]==5) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
		}
		if(x-1>-1 && y+1<12) {
			araSonuc2 = matris[x-1][y]+matris[x-1][y+1]+matris[x][y+1];
			if(araSonuc2%5==0 && matris[x-1][y]==5 && matris[x-1][y+1]==5 && matris[x][y+1]==5) {
				araSonuc = 4;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x-1][y]==5 && matris[x-1][y+1]==5) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x-1][y+1]==5 && matris[x][y+1]==5) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x][y+1]==5 && matris[x-1][y]==5) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x-1][y+1]==5) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x-1][y]==5) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x][y+1]==5) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
		}
		if(x+1<12 && y-1>-1) {
			araSonuc2 = matris[x][y-1]+matris[x+1][y-1]+matris[x+1][y];
			if(araSonuc2%5==0 && matris[x][y-1]==5 && matris[x+1][y-1]==5 && matris[x+1][y]==5) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x][y-1]==5 && matris[x+1][y-1]==5) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x][y-1]==5 && matris[x+1][y]==5) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x+1][y-1]==5 && matris[x+1][y]==5) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x][y-1]==5) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x+1][y-1]==5) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x+1][y]==5) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
		}
		if(x+1<12 && y+1<12) {
			araSonuc2 = matris[x][y+1]+matris[x+1][y]+matris[x+1][y+1];
			if(araSonuc2%5==0 && matris[x][y+1]==5 && matris[x+1][y]==5 && matris[x+1][y+1]==5) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x][y+1]==5 && matris[x+1][y]==5) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x][y+1]==5 && matris[x+1][y+1]==5) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x+1][y]==5 && matris[x+1][y+1]==5) {
				araSonuc = 3;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x][y+1]==5) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x+1][y]==5) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
			if(araSonuc2%5==0 && matris[x+1][y+1]==5) {
				araSonuc = 2;
				if(enb<araSonuc)
					enb = araSonuc;
			}
		}
		return enb;
	}
	
	public int skorHesapla(int x, int y){
		
		// * *
		// * *
		// yukaridaki dort yildiz puan alinabilecek dortlu matrisi temsil ediyor
		// 1 2
		// 3 4
		
		// bunlari rakamsal olarak ifade edersek eger puanlama kontrolu soyle olacak
		// * 2
		// 3 4 
		// 2'yi 3'u, 4'u kontrol et hepsi 1 ile ayni degere sahipse dortlu oldu. 5 puan dondur - return 5 -
		
		// 1 2
		// 3 *
		// puan alinmis mi diye yapilacak kontrolde 1,2,3 noktalariyla * 'in degerleri karsilastirilir.
		//degerler ayniysa 5 puan dondurulur.
		
		// bu islem 4 olasilik oldugu icin 4 durum icin yapilir.
				
		int counter = 1; 
		
		if( y+1 < 12 && matris[x][y] == matris[x][y+1])
			counter++;
		if( ( x-1 > -1 && y+1 < 12) && matris[x][y] == matris[x-1][y+1])
			counter++;
		if( x-1 > -1 && matris[x][y] == matris[x-1][y])
			counter++;
		
		if (counter == 4)
			return 5;
		
		
		counter = 1;	
		if( y-1 > -1 && matris[x][y] == matris[x][y-1])
			counter++;
		if( (y-1 > -1 && x-1 > -1) && matris[x][y] == matris[x-1][y-1])
			counter++;
		if( x-1 > -1 && matris[x][y] == matris[x-1][y])
			counter++;
		
		if (counter == 4)
			return 5;
		
		
		counter = 1;	
		if( y-1 > -1 && matris[x][y] == matris[x][y-1])
			counter++;
		if( (y-1 > -1 && x+1 < 12) && matris[x][y] == matris[x+1][y-1])
			counter++;
		if( x+1 < 12 && matris[x][y] == matris[x+1][y])
			counter++;
		
		if (counter == 4)
			return 5;
		
		counter = 1;	
		if( x+1 < 12 && matris[x][y] == matris[x+1][y])
			counter++;
		if( (x+1 < 12 && y+1 < 12) && matris[x][y] == matris[x+1][y+1])
			counter++;
		if( y+1 < 12 && matris[x][y] == matris[x][y+1])
			counter++;
		
		if (counter == 4)
			return 5;
		return 0;
	}
		
	public void tahtaGuncelle() {
		for(int i=0;i<12;i++)
			for(int j=0;j<12;j++)
				if(matris[i][j]==1)
					kare[i][j].setBackground(Color.blue);
				else if(matris[i][j]==5)
					kare[i][j].setBackground(Color.red);
		txtBilPuan.setText(""+bilPuan);
		txtInsPuan.setText(""+insPuan);
	}
}
